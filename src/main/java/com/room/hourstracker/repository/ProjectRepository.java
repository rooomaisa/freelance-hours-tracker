package com.room.hourstracker.repository;

import com.room.hourstracker.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {}
