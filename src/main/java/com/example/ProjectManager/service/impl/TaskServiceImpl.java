package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.model.Task;
import com.example.ProjectManager.repository.ProjectRepository;
import com.example.ProjectManager.repository.TaskRepository;
import com.example.ProjectManager.service.TaskService;
import jakarta.transaction.Transactional;
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

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Override
    public Boolean projectNotExist(UUID projectId) {
        return projectRepository.findById(projectId).isPresent() ? false : true;
    }

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> findAllTasksForProject(UUID projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Override
    @Transactional
    public Task saveTask(Task task) {
        Task currentTask = taskRepository.save(task);
        projectRepository.findById(task.getProjectId()).get().getTasks().add(currentTask);
        return currentTask;
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return taskRepository.findById(id);
    }

    @Override
    public Task updateTask(Task task) {
        if (!taskRepository.findById(task.getId()).isPresent()) return null;
        Task newTask = taskRepository.findById(task.getId()).get();
        newTask.setName(task.getName());
        newTask.setDescription(task.getDescription());
        newTask.setEndDate(task.getEndDate());
        return taskRepository.save(newTask);
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}
