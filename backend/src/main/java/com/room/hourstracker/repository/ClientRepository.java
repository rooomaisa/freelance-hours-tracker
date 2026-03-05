package com.room.hourstracker.repository;

import com.room.hourstracker.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {}
