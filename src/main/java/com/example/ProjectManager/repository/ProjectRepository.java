package com.example.ProjectManager.repository;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {

    List<Project> findAllByUser(User user);
}
