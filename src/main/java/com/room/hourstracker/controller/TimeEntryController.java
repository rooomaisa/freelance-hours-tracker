package com.room.hourstracker.controller;

import com.room.hourstracker.domain.Project;
import com.room.hourstracker.domain.TimeEntry;
import com.room.hourstracker.repository.ProjectRepository;
import com.room.hourstracker.repository.TimeEntryRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class TimeEntryController {

    private final TimeEntryRepository entries;
    private final ProjectRepository projects;

    public TimeEntryController(TimeEntryRepository entries, ProjectRepository projects) {
        this.entries = entries;
        this.projects = projects;
    }

    // GET all entries
    @GetMapping("/time-entries")
    public List<TimeEntry> getAll() {
        return entries.findAll();
    }

    // GET entries for one project
    @GetMapping("/projects/{projectId}/time-entries")
    public List<TimeEntry> getByProject(@PathVariable Long projectId) {
        return entries.findByProjectId(projectId);
    }

    // GET entries in date range: /api/time-entries?start=2025-01-01&end=2025-01-31
    @GetMapping(value = "/time-entries", params = {"start", "end"})
    public List<TimeEntry> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return entries.findByDateBetween(start, end);
    }

    // Create new entry
    @PostMapping("/time-entries")
    public TimeEntry create(@RequestBody CreateEntryRequest body) {
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

    // Delete entry
    @DeleteMapping("/time-entries/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        entries.deleteById(id);
    }

    // tiny DTO
    public record CreateEntryRequest(Long projectId, LocalDate date, double hours, String notes, boolean billable) {}
}
