package com.example.ProjectManager.controller;

import com.example.ProjectManager.config.SecurityConfig;
import com.example.ProjectManager.model.Task;
import com.example.ProjectManager.repository.UserRepository;
import com.example.ProjectManager.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
@Import(SecurityConfig.class)
@WithMockUser(roles = "ADMIN", password = "admin", username = "admin")
class TaskControllerWebMvcTest {

    private static final String TASK_CREATED = "Задача добавлена";
    private static final String TASK_DELETED = "Задача удалена";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TaskService taskService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void findAllTasks() throws Exception {
        Task task1 = Task.builder()
                .id(UUID.randomUUID())
                .projectId(UUID.randomUUID())
                .name("Test task 1")
                .build();
        Task task2 = Task.builder()
                .id(UUID.randomUUID())
                .projectId(UUID.randomUUID())
                .name("Test task 2")
                .build();
        Task task3 = Task.builder()
                .id(UUID.randomUUID())
                .projectId(UUID.randomUUID())
                .name("Test task 3")
                .build();

        var tasks = List.of(task1, task2, task3);
        String tasksJson = objectMapper.writeValueAsString(tasks);

        when(taskService.findAllTasks()).thenReturn(tasks);

        mockMvc.perform(
                        get("/api/v1/projects/tasks")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(tasksJson))
                .andDo(print());
    }

    @Test
    void findAllTasksForProject() throws Exception {
        UUID projectId = UUID.randomUUID();
        Task task1 = Task.builder()
                .id(UUID.randomUUID())
                .projectId(projectId)
                .name("Test task 1")
                .build();
        Task task2 = Task.builder()
                .id(UUID.randomUUID())
                .projectId(projectId)
                .name("Test task 2")
                .build();
        Task task3 = Task.builder()
                .id(UUID.randomUUID())
                .projectId(projectId)
                .name("Test task 3")
                .build();

        var tasks = List.of(task1, task2, task3);
        String tasksJson = objectMapper.writeValueAsString(tasks);

        when(taskService.findAllTasksForProject(projectId)).thenReturn(tasks);

        mockMvc.perform(
                        get("/api/v1/projects/tasks/tasks_for_project/{projectId}", projectId)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(tasksJson))
                .andDo(print());

    }

    @Test
    void saveTask() throws Exception {
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .name("Test task")
                .build();
        UUID projectId = UUID.randomUUID();

        when(taskService.saveTask(task)).thenReturn(task);
        String taskJson = objectMapper.writeValueAsString(task);

        mockMvc.perform(
                post("/api/v1/projects/tasks/save_task/{projectId}", projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson)
        )
                .andExpect(status().isCreated())
                .andExpect(content().string(TASK_CREATED))
                .andDo(print());
    }
    @Test
    void findById() throws Exception {

        Task task = Task.builder()
                .id(UUID.randomUUID())
                .name("Test task")
                .build();

        when(taskService.findById(task.getId())).thenReturn(Optional.of(task));
        String taskJson = objectMapper.writeValueAsString(task);

        mockMvc.perform(
                get("/api/v1/projects/tasks/{id}", task.getId())
        )
                .andExpect(status().isOk())
                .andExpect(content().string(taskJson))
                .andDo(print());

    }

    @Test
    void updateTask() throws Exception {
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .name("Test task")
                .build();

        String taskJson = objectMapper.writeValueAsString(task);
        when(taskService.updateTask(task)).thenReturn(task);

        mockMvc.perform(
                put("/api/v1/projects/tasks/update_task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(taskJson))
                .andDo(print());
    }

    @Test
    void deleteTask() throws Exception {
        UUID id = UUID.randomUUID();
        Task task = Task.builder()
                .name("Test task")
                .id(id)
                .build();

        mockMvc.perform(
                delete("/api/v1/projects/tasks/delete_task/{id}", id)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(TASK_DELETED))
                .andDo(print());
    }
}