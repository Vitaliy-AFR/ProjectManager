package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.Exceptions.NotFoundException;
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

    private static final String PROJECT_NOT_EXIST = "Такого проекта не существует";
    private static final String TASK_NOT_EXIST = "Такой задачи не существует";
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
    public List<Task> findAllTasksForProject(UUID projectId) throws NotFoundException {
        if (projectNotExist(projectId)) {
            throw new NotFoundException(PROJECT_NOT_EXIST);
        }
        return taskRepository.findByProjectId(projectId);
    }

    @Override
    @Transactional
    public Task saveTask(Task task) throws NotFoundException {
        UUID projectId = task.getProjectId();
        if (projectRepository.findById(projectId).isEmpty()) {
            throw new NotFoundException(PROJECT_NOT_EXIST);
        }
        Task correctTask;
        if (task.getId() == null) {
            correctTask = Task.builder()
                    .projectId(task.getProjectId())
                    .id(UUID.randomUUID())
                    .description(task.getDescription())
                    .endDate(task.getEndDate())
                    .build();
        } else {
            correctTask = task;
        }

        projectRepository.findById(correctTask.getProjectId()).get().getTasks().add(correctTask);
        return taskRepository.save(correctTask);
    }

    @Override
    public Optional<Task> findById(UUID id) throws NotFoundException {
        if (taskRepository.findById(id).isEmpty()) {
            throw new NotFoundException(TASK_NOT_EXIST);
        }
        return taskRepository.findById(id);
    }

    @Override
    public Task updateTask(Task task) throws NotFoundException {
        if (taskRepository.findById(task.getId()).isEmpty()) {
            throw new NotFoundException(TASK_NOT_EXIST);
        }
        Task newTask = taskRepository.findById(task.getId()).get();
        newTask.setName(task.getName());
        newTask.setDescription(task.getDescription());
        newTask.setEndDate(task.getEndDate());
        return taskRepository.save(newTask);
    }

    @Override
    public void deleteTask(UUID id) throws NotFoundException {
        if (taskRepository.findById(id).isEmpty()) {
            throw new NotFoundException(TASK_NOT_EXIST);
        }
        taskRepository.deleteById(id);
    }
}
