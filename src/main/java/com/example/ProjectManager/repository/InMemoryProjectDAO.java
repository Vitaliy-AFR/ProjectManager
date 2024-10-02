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

}
