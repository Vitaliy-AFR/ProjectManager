package com.example.ProjectManager.controller;

import com.example.ProjectManager.Config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerWebMvcTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void addUser() {
    }

    @Test
    void findAllUsers() {
    }

    @Test
    void findByName() {
    }

    @Test
    void deleteUser() {
    }
}