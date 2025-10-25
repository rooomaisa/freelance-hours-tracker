package com.room.hourstracker.repository;

import com.room.hourstracker.domain.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
    List<TimeEntry> findByProjectId(Long projectId);
    List<TimeEntry> findByDateBetween(LocalDate start, LocalDate end);
}
