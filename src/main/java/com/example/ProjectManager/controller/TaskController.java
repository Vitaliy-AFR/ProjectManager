package com.example.ProjectManager.controller;

import com.example.ProjectManager.model.Task;
import com.example.ProjectManager.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/projects/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService service;

    @GetMapping
    public List<Task> findAllTasks() {
        return service.findAllTasks();
    }

    @GetMapping("tasks_for_project/{projectId}")
    public List<Task> findAllTasksForProject(@PathVariable UUID projectId) {
        return service.findAllTasksForProject(projectId);
    }

    @PostMapping("save_task/{projectId}")
    public String saveTask(@PathVariable UUID projectId, @RequestBody Task task) {
        if (service.projectNotExist(projectId)) return "Нельзя добавить задачу в несуществующий проект";
        task.setProjectId(projectId);
        service.saveTask(task);
        return "Задача добавлена";
    }

    @GetMapping("/{id}")
    public Optional<Task> findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PutMapping("update_task")
    public Task updateTask(@RequestBody Task task) {
        if (!service.findById(task.getId()).isPresent()) return null;
        return service.updateTask(task);
    }

    @DeleteMapping("delete_task/{id}")
    public String deleteTask(@PathVariable UUID id) {
        if (service.findById(id).isPresent()){
            service.deleteTask(id);
            return "Задача удалена";
        } else return "Такой задачи не существует";
    }



}
