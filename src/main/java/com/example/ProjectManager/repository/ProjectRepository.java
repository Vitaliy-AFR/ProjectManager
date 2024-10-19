package com.example.ProjectManager.repository;

import com.example.ProjectManager.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}
