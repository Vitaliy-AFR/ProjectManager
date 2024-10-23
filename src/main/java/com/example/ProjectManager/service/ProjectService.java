package com.example.ProjectManager.service;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectService {
    List<Project> findAllProjects();
    Project saveProject(Project project);
    Optional<Project> findById(UUID id);
    Project updateProject(Project project);
    void deleteProject(UUID id);
}
