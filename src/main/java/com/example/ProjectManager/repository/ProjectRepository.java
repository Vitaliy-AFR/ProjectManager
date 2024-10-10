package com.example.ProjectManager.repository;

import com.example.ProjectManager.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    default Project updateProject(Project project){
        if (!findById(project.getId()).isPresent()) return null;
        Project newProject = findById(project.getId()).get();
        newProject.setName(project.getName());
        newProject.setDescription(project.getDescription());
        newProject.setEndDate(project.getEndDate());
        return save(newProject);
    }
}
