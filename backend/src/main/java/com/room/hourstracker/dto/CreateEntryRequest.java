package com.room.hourstracker.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateEntryRequest(
        @NotNull Long projectId,
        @NotNull LocalDate date,
        @Positive @Max(24) double hours,
        @Size(max = 500) String notes,
        boolean billable
) {}