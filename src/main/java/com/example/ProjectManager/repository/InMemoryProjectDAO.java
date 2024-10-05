package com.example.ProjectManager.repository;

import com.example.ProjectManager.model.Project;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryProjectDAO {

    private final Map<UUID, Project> PROJECTS = new HashMap<>();

    public Map<UUID, Project> findAllProjects() {
        return PROJECTS;
    }

    public Project saveProject(UUID id, Project project) {
        PROJECTS.put(id, project);
        return project;
    }

    public Project findById (UUID id) {
        return PROJECTS.getOrDefault(id, null);
    }

    public Project updateProject(Project project) {
        UUID id = project.getId();
        if (PROJECTS.containsKey(id)) {
            PROJECTS.get(id).setName(project.getName());
            PROJECTS.get(id).setDescription(project.getDescription());
            PROJECTS.get(id).setEndDate(project.getEndDate());
            return PROJECTS.get(id);
        }
        return null;
    }

    public void deleteProject (UUID id) {
        var project = findById(id);
        if (project != null) {
            PROJECTS.remove(id);
        }

    }


}
