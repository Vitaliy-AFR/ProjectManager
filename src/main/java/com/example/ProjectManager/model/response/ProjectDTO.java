package com.example.ProjectManager.model.response;

import com.example.demo.model.response.PersonDTO;

import java.time.LocalDateTime;
import java.util.List;


public record ProjectDTO(
        String name,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        PersonDTO person,
        List<TaskDTO> tasks
) {
}
