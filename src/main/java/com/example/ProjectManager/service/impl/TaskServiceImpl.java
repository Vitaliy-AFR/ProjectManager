package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.model.Task;
import com.example.ProjectManager.repository.ProjectRepository;
import com.example.ProjectManager.repository.TaskRepository;
import com.example.ProjectManager.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository repository;
    private final ProjectRepository projectRepository;

    @Override
    public Boolean projectNotExist(UUID projectId) {
        return projectRepository.findById(projectId).isPresent() ? false : true;
    }

    @Override
    public List<Task> findAllTasks() {
        return repository.findAll();
    }

    @Override
    public List<Task> findAllTasksForProject(UUID projectId) {
        return repository.findByProjectId(projectId);
//        return projectRepository.findById(projectId).orElse(null).getTasks();
    }

    @Override
    public Task saveTask(Task task) {
        Task currentTask = repository.save(task);
        projectRepository.findById(task.getProjectId()).get().getTasks().add(currentTask);
        return currentTask;
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public Task updateTask(Task task) {
        if (!repository.findById(task.getId()).isPresent()) return null;
        Task newTask = repository.findById(task.getId()).get();
        newTask.setName(task.getName());
        newTask.setDescription(task.getDescription());
        newTask.setEndDate(task.getEndDate());
        return repository.save(newTask);
    }

    @Override
    public void deleteTask(UUID id) {
        repository.deleteById(id);
    }
}
