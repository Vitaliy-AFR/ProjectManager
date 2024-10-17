package com.example.ProjectManager.controller;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.repository.ProjectRepository;
import com.example.ProjectManager.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest(ProjectController.class)
//@WithMockUser(roles = "ADMIN", password = "admin", username = "admin")

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest  {

    @Mock
    ProjectRepository projectRepository;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    //Тесты по урокам letsCode
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

//    Данный тест не выполняется. Вообще какие то проблемы с формой авторизации. В том числе при работе в postman
    @Test
    public void loginTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/projects"))
                .andExpect(redirectedUrl("http://localhost:8080/login"));
    }


    //Тесты по урокам letsCode
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }


    //Тесты по урокам letsCode
    @Test
    public void findAllProjectsTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/projects"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    //Тесты по урокам letsCode
    @Test
    public void saveProjectTest() throws Exception {
        Project project = Project.builder().name("Test project 1").build();
        String projectJson = objectMapper.writeValueAsString(project);
        this.mockMvc.perform(post("/api/v1/projects/save_project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(projectJson))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //Тесты по урокам Уголок сельского джависта
    // этот тест пока не работает
    @Test
    public void findAllProjects_ReturnsValidResponseEntity(){
        //given
        var projects = List.of(
                Project.builder().name("Test project 1").build(),
                Project.builder().name("Test project 2").build(),
                Project.builder().name("Test project 3").build()
        );
        doReturn(projects).when(this.projectRepository).findAll();

        //when
        var responseEntity = this.projectController.findAllProjects();

        //then
        assertNotNull(responseEntity);
    }



}
