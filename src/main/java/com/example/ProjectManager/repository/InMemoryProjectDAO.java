package com.example.ProjectManager.repository;

import com.example.ProjectManager.model.Project;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryProjectDAO {

//    private final Map<UUID, Project> PROJECTS = new HashMap<>();

    private final List<Project> PROJECTS = new ArrayList<>();

    public List<Project> findAllProjects() {
        return PROJECTS;
    }

    public Project saveProject(Project project) {
        PROJECTS.add(project);
        return project;
    }

    public Project findById (UUID id) {
        return PROJECTS.stream().filter(v -> v.getId().equals(id)).findAny().orElse(null);
    }

    public Project updateProject(Project project) {
        int i = PROJECTS.indexOf(project);
        if (PROJECTS.contains(project)) {
            PROJECTS.get(i).setName(project.getName());
            PROJECTS.get(i).setDescription(project.getDescription());
            PROJECTS.get(i).setEndDate(project.getEndDate());
            return PROJECTS.get(i);
        }
        return null;
    }

    public void deleteProject (UUID id) {
        var project = findById(id);
        if (project != null) {
            PROJECTS.remove(project);
        }

    }


}
