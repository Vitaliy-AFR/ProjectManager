package com.example.ProjectManager.repository;

import com.example.ProjectManager.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    default Map<UUID, Project> findAllMap() {
        return findAll().stream().collect(Collectors.toMap(Project::getId, v -> v));
    }

    default Project findByIdFromMap(UUID id) {
        return findAll().stream().filter(v -> v.getId().equals(id)).findFirst().orElse(null);
    }

}
