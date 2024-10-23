package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.model.User;
import com.example.ProjectManager.repository.InMemoryProjectDAO;
import com.example.ProjectManager.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InMemoryProjectServiceImpl implements ProjectService {

    private final InMemoryProjectDAO repository;

    @Override
    public List<Project> findAllProjects() {
        return repository.findAllProjects();
    }

    @Override
    public Project saveProject(Project project) {
        return repository.saveProject(project);
    }

    @Override
    public Optional<Project> findById(UUID id) {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public Project updateProject(Project project) {
        return repository.updateProject(project);
    }

    @Override
    public void deleteProject(UUID id) {
        repository.deleteProject(id);
    }
}
