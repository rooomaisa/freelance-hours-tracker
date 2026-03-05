package com.room.hourstracker.service;
import com.room.hourstracker.domain.TimeEntry;
import com.room.hourstracker.repository.ProjectRepository;
import com.room.hourstracker.repository.TimeEntryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import com.room.hourstracker.dto.CreateEntryRequest;
import com.room.hourstracker.domain.Project;
import com.room.hourstracker.domain.TimeEntry;
import com.room.hourstracker.dto.CreateEntryRequest;

@Service
public class TimeEntryService {

    private final TimeEntryRepository entries;
    private final ProjectRepository projects;

    public TimeEntryService(TimeEntryRepository entries, ProjectRepository projects) {
        this.entries = entries;
        this.projects = projects;
    }

    public List<TimeEntry> getAll() {
        return entries.findAll();
    }

    public List<TimeEntry> getByProject(Long projectId) {
        return entries.findByProjectId(projectId);
    }

    public List<TimeEntry> getByDateRange(LocalDate start, LocalDate end) {
        return entries.findByDateBetween(start, end);
    }

    public TimeEntry create(CreateEntryRequest body) {
        Project project = projects.findById(body.projectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Project not found"));

        TimeEntry e = new TimeEntry();
        e.setProject(project);
        e.setDate(body.date());
        e.setHours(body.hours());
        e.setNotes(body.notes());
        e.setBillable(body.billable());

        return entries.save(e);
    }

    public void delete(Long id) {
        if (!entries.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Time entry not found");
        }
        entries.deleteById(id);
    }
}