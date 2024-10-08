package com.example.ProjectManager.service;

import com.example.ProjectManager.model.Project;

import java.util.Map;
import java.util.UUID;

public interface ProjectService {
    Map<UUID, Project> findAllProjects();
    Project saveProject(Project project);
    Project findById(UUID id);
    Project updateProject(Project project);
    void deleteProject(UUID id);
}
