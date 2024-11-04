package com.example.ProjectManager.repository;

import com.example.ProjectManager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("SELECT task FROM Task task WHERE task.projectId = ?1")
    List<Task> findByProjectId(UUID projectId);

    @Query(value = "SELECT * FROM tasks WHERE tasks.project_id = ?1 LIMIT 1", nativeQuery = true) //сделать, чтобы выдавал только первую таску
    List<Task> findFirstByProjectId(UUID projectId);

    void deleteByProjectId(UUID projectId);

}
