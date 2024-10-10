package com.example.ProjectManager.service;
import com.example.ProjectManager.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> findAllTasks();
    List<Task> findAllTasksForProject(UUID projectId);
    Task saveTask(Task task);
    Optional<Task> findById(UUID id);
    Task updateTask(Task task);
    void deleteTask(UUID id);
    Boolean projectNotExist(UUID projectId);
}
