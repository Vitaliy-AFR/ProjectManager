package com.example.ProjectManager.controller;

import com.example.ProjectManager.Config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(TaskController.class)
@Import(SecurityConfig.class)
class TaskControllerWebMvcTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void findAllTasks() {
    }

    @Test
    void findAllTasksForProject() {
    }

    @Test
    void saveTask() {
    }

    @Test
    void findById() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void deleteTask() {
    }
}