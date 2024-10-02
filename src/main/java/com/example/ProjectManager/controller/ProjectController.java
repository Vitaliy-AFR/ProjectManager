package com.example.ProjectManager.controller;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService service;

    @GetMapping
    public Map<UUID, Project> findAllProjects() {
        return service.findAllProjects();
    }

}
