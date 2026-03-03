package com.room.hourstracker.controller;

import com.room.hourstracker.domain.TimeEntry;
import com.room.hourstracker.service.TimeEntryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5174")
public class TimeEntryController {

    private final TimeEntryService service;

    public TimeEntryController(TimeEntryService service) {
        this.service = service;
    }

    @GetMapping("/time-entries")
    public List<TimeEntry> getAll() {
        return service.getAll();
    }

    @GetMapping("/projects/{projectId}/time-entries")
    public List<TimeEntry> getByProject(@PathVariable Long projectId) {
        return service.getByProject(projectId);
    }

    @GetMapping(value = "/time-entries", params = {"start", "end"})
    public List<TimeEntry> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return service.getByDateRange(start, end);
    }

    @PostMapping("/time-entries")
    public TimeEntry create(@RequestBody CreateEntryRequest body) {
        return service.create(body);
    }

    @DeleteMapping("/time-entries/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    public record CreateEntryRequest(Long projectId, LocalDate date, double hours, String notes, boolean billable) {}
}
