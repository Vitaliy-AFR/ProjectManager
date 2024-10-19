package com.example.ProjectManager.repository;

import com.example.ProjectManager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    //сделать один из запросов через query
    @Query
    List<Task> findByProjectId(UUID projectId);

}
