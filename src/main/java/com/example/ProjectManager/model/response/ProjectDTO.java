package com.example.ProjectManager.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


public record ProjectDTO(
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        UserDTO user,
        List<TaskDTO> tasks
) {
}
