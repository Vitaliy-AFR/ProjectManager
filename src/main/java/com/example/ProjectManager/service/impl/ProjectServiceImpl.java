package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.repository.ProjectRepository;
import com.example.ProjectManager.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
@Primary
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;

    @Override
    public Map<UUID, Project> findAllProjects() {
        return repository.findAllMap();
    }

    @Override
    public Project saveProject(Project project) {
        return repository.save(project);
    }

    @Override
    public Project findById(UUID id) {
        return repository.findByIdFromMap(id);
    }

    @Override
    public Project updateProject(Project project) {
        return repository.save(project);
    }

    @Override
    @Transactional
    public void deleteProject(UUID id) {
        repository.delete(repository.findByIdFromMap(id));

    }
}
