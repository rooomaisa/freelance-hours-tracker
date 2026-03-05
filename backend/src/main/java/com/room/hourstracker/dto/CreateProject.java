package com.room.hourstracker.dto;

public record CreateProject (
        String name,
        Boolean active,
        Long clientId // optional for now; can be null
) {}
