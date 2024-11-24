package com.example.ProjectManager.service;
import com.example.ProjectManager.Exceptions.NotFoundException;
import com.example.ProjectManager.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> findAllTasks();
    List<Task> findAllTasksForProject(UUID projectId) throws NotFoundException;
    Task saveTask(Task task) throws NotFoundException;
    Optional<Task> findById(UUID id) throws NotFoundException;
    Task updateTask(Task task) throws NotFoundException;
    void deleteTask(UUID id) throws NotFoundException;
    Boolean projectNotExist(UUID projectId) throws NotFoundException;
}
