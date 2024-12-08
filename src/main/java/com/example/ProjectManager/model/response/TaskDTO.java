package com.example.ProjectManager.model.response;

import java.time.LocalDateTime;

public record TaskDTO (
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
) {}
