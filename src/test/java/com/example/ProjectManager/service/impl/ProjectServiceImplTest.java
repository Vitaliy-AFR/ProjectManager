package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.Config.MyUserDetails;
import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.model.User;
import com.example.ProjectManager.repository.ProjectRepository;
import com.example.ProjectManager.repository.TaskRepository;
import com.example.ProjectManager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        ProjectServiceImpl.class
})
class ProjectServiceImplTest {

    @MockBean
    ProjectRepository projectRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    TaskRepository taskRepository;

    @MockBean
    MyUserDetails userDetails;

    @MockBean
    Authentication authentication;

    @MockBean
    SecurityContext securityContext;

    @Autowired
    ProjectServiceImpl projectService;


    @BeforeEach
    void setUp() {
        when(securityContext.getAuthentication()).thenReturn(authentication); // Привязка контекста к объекту
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext); // Размещаем dummy-объекты в контексте
    }

    @Test
    void findAllProjects_forRoleAdmin() {

        //given
        User admin = User.builder().name("admin").build();
        when(userRepository.findByName(null)).thenReturn(Optional.of(admin));

        //when
        projectService.findAllProjects();

        //then
        verify(projectRepository, times(1)).findAll();

    }

    @Test
    void findAllProjects_forAnotherRole() {

        //given
        User user = User.builder().name("user").build();
        when(userRepository.findByName(null)).thenReturn(Optional.of(user));

        //when
        projectService.findAllProjects();

        //then
        verify(projectRepository, times(1)).findAllByUser(user);

    }

    @Test
    void saveProject() {

        //given
        Project project = Project.builder().name("Test project 1").build();

        //when
        projectService.saveProject(project);

        //then
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void findById() {

        //given
        UUID id = UUID.randomUUID();

        //when
        projectService.findById(id);

        //then
        verify(projectRepository, times(1)).findById(id);

    }

    @Test
    void updateProject_idExist() {

        //given
        Project project = Project.builder().name("Test project 1").build();
        doReturn(Optional.of(project) ).when(projectRepository).findById(project.getId());

        //when
        projectService.updateProject(project);

        //then
        verify(projectRepository, times(2)).findById(project.getId());
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void updateProject_idNotExist() {

        //given
        Project project = Project.builder().name("Test project 1").build();
        doReturn(Optional.empty() ).when(projectRepository).findById(project.getId());

        //when
        projectService.updateProject(project);

        //then
        verify(projectRepository, times(1)).findById(project.getId());
        verify(projectRepository, times(0)).save(project);
    }

    @Test
    void deleteProject() {

        //given
        UUID id = UUID.randomUUID();

        //when
        projectService.deleteProject(id);

        //then
        InOrder inOrder = Mockito.inOrder(taskRepository, projectRepository);
        inOrder.verify(taskRepository, times(1)).deleteByProjectId(id);
        inOrder.verify(projectRepository, times(1)).deleteById(id);
    }
}