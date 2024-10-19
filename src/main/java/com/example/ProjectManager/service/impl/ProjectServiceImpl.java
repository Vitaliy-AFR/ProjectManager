package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.repository.ProjectRepository;
import com.example.ProjectManager.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Primary
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;

    @Override
    public List<Project> findAllProjects() {
        return repository.findAll();
    }

    @Override
    public Project saveProject(Project project) {
        return repository.save(project);
    }

    @Override
    public Optional<Project> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Project updateProject(Project project) {
        if (!repository.findById(project.getId()).isPresent()) return null;
        Project newProject = repository.findById(project.getId()).get();
        newProject.setName(project.getName());
        newProject.setDescription(project.getDescription());
        newProject.setEndDate(project.getEndDate());
        return repository.save(newProject);
    }

    @Override
    @Transactional
    public void deleteProject(UUID id) {
        repository.deleteById(id);
    }
}
