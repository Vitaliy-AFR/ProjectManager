package com.example.ProjectManager.controller;

import com.example.ProjectManager.Config.SecurityConfig;
import com.example.ProjectManager.model.User;
import com.example.ProjectManager.repository.UserRepository;
import com.example.ProjectManager.service.UserService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerWebMvcTest {

    private static final String USER_CREATED = "Пользователь добавлен";
    private static final String USER_DELETED = "Пользователь удален";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Test
    void addUser() throws Exception {

        User user = User.builder()
                .name("admin")
                .password("admin")
                .roles("ROLE_ADMIN")
                .build();
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(
                post("/api/v1/users/new_user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson)
        )
                .andExpect(status().isCreated())
                .andExpect(content().string(USER_CREATED))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN", password = "admin", username = "admin")
    void findAllUsers() throws Exception {
        User user = User.builder()
                .name("user")
                .password("user")
                .roles("ROLE_USER")
                .build();
        User admin = User.builder()
                .name("admin")
                .password("admin")
                .roles("ROLE_ADMIN")
                .build();

        var users = List.of(
                user,
                admin
        );

        when(userService.findAllUsers()).thenReturn(users);

        String usersJson = objectMapper.writeValueAsString(users);

        mockMvc.perform(
                get("/api/v1/users")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(usersJson))
                .andDo(print());

    }

    @Test
    @WithMockUser(roles = "ADMIN", password = "admin", username = "admin")
    void findByName() throws Exception {
        User user = User.builder()
                .name("user")
                .password("user")
                .roles("ROLE_USER")
                .build();

        when(userService.findByName(user.getName())).thenReturn(Optional.of(user));
        String userJson = objectMapper.writeValueAsString(user);

        mockMvc.perform(
                get("/api/v1/users/{name}", user.getName())
        )
                .andExpect(content().json(userJson))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @WithMockUser(roles = "ADMIN", password = "admin", username = "admin")
    void deleteUser() throws Exception {
        String name = "user";
        mockMvc.perform(
                delete("/api/v1/users/delete_user/{name}", name)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(USER_DELETED))
                .andDo(print());
    }
}