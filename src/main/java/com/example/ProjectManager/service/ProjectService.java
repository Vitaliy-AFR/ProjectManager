package com.example.ProjectManager.service;

import com.example.ProjectManager.Exceptions.NotFoundException;
import com.example.ProjectManager.model.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectService {
    List<Project> findAllProjects();
    Project saveProject(Project project);
    Optional<Project> findById(UUID id) throws NotFoundException;
    Project updateProject(Project project) throws NotFoundException;
    void deleteProject(UUID id) throws NotFoundException;
}
