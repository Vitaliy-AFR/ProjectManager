package com.example.ProjectManager.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class Project {

    private final UUID id = UUID.randomUUID();
    private String name;
    private String description;
    private final LocalDateTime startDate = LocalDateTime.now();
    private LocalDateTime endDate;
    private final Map<UUID, Task> tasks = new HashMap<>();


}
