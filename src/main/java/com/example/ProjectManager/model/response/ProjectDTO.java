package com.example.ProjectManager.model.response;

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
