package com.room.hourstracker.repository;

import com.room.hourstracker.domain.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {}
