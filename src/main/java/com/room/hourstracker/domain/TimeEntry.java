package com.room.hourstracker.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TimeEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Project project;

    @NotNull
    private LocalDate date;

    @Min(0)
    private double hours; // e.g., 1.5

    private String notes;

    @Column(nullable = false)
    private boolean billable = true;
}
