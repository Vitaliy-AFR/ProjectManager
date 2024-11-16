package com.example.ProjectManager.controller;

import com.example.ProjectManager.Config.SecurityConfig;
import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.model.Task;
import com.example.ProjectManager.model.User;
import com.example.ProjectManager.repository.UserRepository;
import com.example.ProjectManager.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
@Import(SecurityConfig.class)
@TestPropertySource("classpath:application-test.yml")
class ProjectControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectService projectService;

    @MockBean
    private UserRepository userRepository;

    private static final String PROJECT_ADDED = "Проект добавлен";

    User admin = User.builder()
            .name("admin")
            .password("admin")
            .roles("ROLE_ADMIN")
            .build();

    User user = User.builder()
            .name("user")
            .password("user")
            .roles("ROLE_ADMIN")
            .build();

    @Test
    void check_contextStarts(){
        assertNotNull(mockMvc);
    }

    //нужно добавлять базу данных
    @Test
    //sql запрос пока не работает, еще не разобрался, а без него тест проходит нормально
//    @Sql(scripts = {
//            "/data/cleanUp.sql",
//            "/data/insertData.sql"
//    })
    @WithMockUser(roles = "ADMIN", password = "admin", username = "admin")
    void findAllProjects() throws Exception {
        Project project1 = Project.builder()
                .name("Test project 1")
                .description("Description test 1")
                .tasks(new ArrayList<>())
                .user(admin)
                .build();
        Project project2 = Project.builder()
                .name("Test project 2")
                .description("Description test 2")
                .tasks(new ArrayList<>())
                .user(user)
                .build();
        Project project3 = Project.builder()
                .name("Test project 3")
                .description("Description test 3")
                .build();
        Task task1 = Task.builder()
                .name("Test task 1")
                .projectId(project1.getId())
                .description("Description task test 1")
                .build();
        project1.getTasks().add(task1);
        var projects = List.of(
                project1,
                project2,
                project3
        );

        when(projectService.findAllProjects()).thenReturn(projects);

        String projectsJson = objectMapper.writeValueAsString(projects);

        mockMvc.perform(
                get("/api/v1/projects")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(projectsJson))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN", password = "admin", username = "admin")
    void saveProject() throws Exception {
        Project project = Project.builder()
                .name("Test project")
                .build();
        UUID id = project.getId();
        String projectJson = objectMapper.writeValueAsString(project);

        mockMvc.perform(
                        post("/api/v1/projects/save_project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(new MediaType (MediaType.TEXT_PLAIN, StandardCharsets.UTF_8)))
                .andExpect(content().string(PROJECT_ADDED))
                .andDo(print());
    }

    @Test
    @WithMockUser(roles = "ADMIN", password = "admin", username = "admin")
    void findById() throws Exception {
        Project project = Project.builder()
                .name("Test project")
                .build();
        UUID id = project.getId();
        //переводим дату создания проекта в строку и удаляем завершающие нули, т.к. сервер тоже их удаляет
        String startDate = project.getStartDate().toString().replaceAll("()\\.0+$|(\\..+?)0+$", "$2");

        when(projectService.findById(id)).thenReturn(Optional.of(project));

        mockMvc.perform(
                get("/api/v1/projects/{id}", id)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(id.toString()))
                .andExpect(jsonPath("name").value("Test project"))
                .andExpect(jsonPath("startDate").value(startDate))
                .andDo(print());
    }

    @Test
    void updateProject() {
    }

    @Test
    void deleteProject() {
    }
}