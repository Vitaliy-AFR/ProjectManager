package com.example.ProjectManager.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Task {

    private final UUID id = UUID.randomUUID();
    private final UUID projectId;
    private String name;
    private String description;
    private final LocalDateTime startDate = LocalDateTime.now();
    private LocalDateTime endDate;

}
