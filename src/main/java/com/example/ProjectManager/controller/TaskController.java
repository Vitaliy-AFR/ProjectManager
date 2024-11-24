package com.example.ProjectManager.controller;

import com.example.ProjectManager.Exceptions.NotFoundException;
import com.example.ProjectManager.model.Task;
import com.example.ProjectManager.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/projects/tasks")
@AllArgsConstructor
public class TaskController {

    private static final String TASK_CREATED = "Задача добавлена";
    private static final String TASK_DELETED = "Задача удалена";
    private final TaskService taskService;

    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Task>>  findAllTasks() {
        return ResponseEntity.ok()
                        .body(taskService.findAllTasks());
    }

    @GetMapping("tasks_for_project/{projectId}")
    public ResponseEntity<List<Task>> findAllTasksForProject(@PathVariable UUID projectId) throws NotFoundException {
        return ResponseEntity.ok()
                        .body(taskService.findAllTasksForProject(projectId));
    }

    @PostMapping("save_task/{projectId}")
    public ResponseEntity<String> saveTask(@PathVariable UUID projectId, @RequestBody Task task) throws NotFoundException {
        task.setProjectId(projectId);
        taskService.saveTask(task);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TASK_CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok()
                        .body(taskService.findById(id).get());
    }

    @PutMapping("update_task")
    public Task updateTask(@RequestBody Task task) throws NotFoundException {
        return taskService.updateTask(task);
    }

    @DeleteMapping("delete_task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID id) throws NotFoundException {
        taskService.deleteTask(id);
        return ResponseEntity.ok()
                .body(TASK_DELETED);
    }



}
