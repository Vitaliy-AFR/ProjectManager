package com.example.ProjectManager.service;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.repository.ProjectRepository;
import com.example.ProjectManager.repository.TaskRepository;
import com.example.ProjectManager.repository.UserRepository;
import com.example.ProjectManager.service.impl.ProjectServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceImplTest {

    @Mock
    ProjectRepository projectRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    TaskRepository taskRepository;

    @InjectMocks
    ProjectServiceImpl projectService;


    @BeforeEach
    void setUp() {
        Authentication authentication = Mockito.mock(Authentication.class); // Имитация объекта
        SecurityContext securityContext = Mockito.mock(SecurityContext.class); // Имитация контекста
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication); // Привязка контекста к объекту
        SecurityContextHolder.setContext(securityContext); // Размещаем dummy-объекты в контексте
    }

    @Test
    void findAllProjects() {
        //given
        Project project1 = new Project();
        project1.setName("Test project 1");
        Project project2 = new Project();
        project2.setName("Test project 2");
        Project project3 = new Project();
        project3.setName("Test project 3");
        var projects = List.of(
                project1,
                project2,
                project3
        );
        when(projectRepository.findAll()).thenReturn(projects);

        //when
        var allProjects = projectService.findAllProjects();

        //then
        Assertions.assertEquals(projects, allProjects);

    }

    @Test
    void saveProject() {
    }

    @Test
    void findById() {
    }

    @Test
    void updateProject() {
    }

    @Test
    void deleteProject() {
    }
}