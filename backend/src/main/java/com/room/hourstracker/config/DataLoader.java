package com.room.hourstracker.config;

import com.room.hourstracker.domain.*;
import com.room.hourstracker.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(ClientRepository clients, ProjectRepository projects, TimeEntryRepository entries) {
        return args -> {
            if (clients.count() > 0) return;

            Client acme = clients.save(Client.builder().name("Acme Co").email("billing@acme.test").build());
            Project website = projects.save(Project.builder().client(acme).name("Website Revamp").active(true).build());

            entries.save(TimeEntry.builder()
                    .project(website).date(LocalDate.now()).hours(2.0).notes("Kickoff call").billable(true).build());

            entries.save(TimeEntry.builder()
                    .project(website).date(LocalDate.now().minusDays(1)).hours(3.5).notes("Landing page").billable(true).build());
        };
    }
}
