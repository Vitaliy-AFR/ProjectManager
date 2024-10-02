package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.repository.InMemoryProjectDAO;
import com.example.ProjectManager.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InMemoryProjectServiceImpl implements ProjectService {

    private final InMemoryProjectDAO repository;

    @Override
    public Map<UUID, Project> findAllProjects() {
        return repository.findAllProjects();
    }

    @Override
    public Project saveProject(Project project) {
        return repository.saveProject(project.getId(), project);
    }
}
