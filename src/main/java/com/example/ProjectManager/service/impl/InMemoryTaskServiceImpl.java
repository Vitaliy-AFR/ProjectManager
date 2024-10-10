package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.model.Task;
import com.example.ProjectManager.repository.InMemoryTaskDAO;
import com.example.ProjectManager.service.TaskService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InMemoryTaskServiceImpl implements TaskService {

    private InMemoryTaskDAO repository;

    @Override
    public Boolean projectNotExist(UUID projectId) {
        return repository.projectNotExist(projectId);
    }

    @Override
    public List<Task> findAllTasks() {
        return repository.findAllTasks();
    }

    @Override
    public List<Task> findAllTasksForProject(UUID projectId) {
        return repository.findAllTasksForProject(projectId);
    }

    @Override
    public Task saveTask(Task task) {
        return repository.saveTask(task);
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return Optional.ofNullable(repository.findById(id));
    }

    @Override
    public Task updateTask(Task task) {
        return repository.updateTask(task);
    }

    @Override
    public void deleteTask(UUID id) {
        repository.deleteTask(id);
    }
}
