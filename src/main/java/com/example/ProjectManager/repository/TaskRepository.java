package com.example.ProjectManager.repository;

import com.example.ProjectManager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT task FROM Task task WHERE task.projectId = ?1")
    List<Task> findByProjectId(UUID projectId);

}
