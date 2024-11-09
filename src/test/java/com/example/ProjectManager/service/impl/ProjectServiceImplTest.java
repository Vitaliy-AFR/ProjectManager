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

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
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

    final String ADMIN_NAME = "admin";
    final String USER_NAME = "user";


    @BeforeEach
    void setUp() {
        when(securityContext.getAuthentication()).thenReturn(authentication); // Привязка контекста к объекту
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext); // Размещаем dummy-объекты в контексте
    }

    @Test
    void findAllProjects_forRoleAdmin() {

        //given
        User admin = User.builder().name(ADMIN_NAME).build();
        when(userDetails.getUsername()).thenReturn(ADMIN_NAME);
        when(userRepository.findByName(ADMIN_NAME)).thenReturn(Optional.of(admin));

        //when
        projectService.findAllProjects();

        //then
        InOrder inOrder = inOrder(userRepository, projectRepository);

        inOrder.verify(userRepository, times(1)).findByName(ADMIN_NAME);
        inOrder.verify(projectRepository, times(1)).findAll();

    }

    @Test
    void findAllProjects_forAnotherRole() {

        //given
        User user = User.builder().name(USER_NAME).build();
        when(userDetails.getUsername()).thenReturn(USER_NAME);
        when(userRepository.findByName(USER_NAME)).thenReturn(Optional.of(user));

        //when
        projectService.findAllProjects();

        //then
        InOrder inOrder = inOrder(userRepository, projectRepository);

        inOrder.verify(userRepository, times(1)).findByName(USER_NAME);
        inOrder.verify(projectRepository, times(1)).findAllByUser(user);

    }

    @Test
    void saveProject_ifUserIsPresent() {

        //given
        Project project = Project.builder().name("Test project 1").build();
        User user = User.builder().name(USER_NAME).build();
        when(userDetails.getUsername()).thenReturn(USER_NAME);
        when(userRepository.findByName(USER_NAME)).thenReturn(Optional.of(user));

        //when
        projectService.saveProject(project);

        //then
        assertEquals(user, project.getUser());

        InOrder inOrder = inOrder(userRepository, projectRepository);

        inOrder.verify(userRepository, times(1)).findByName(USER_NAME);
        inOrder.verify(projectRepository, times(1)).save(project);
    }

    @Test
    void saveProject_ifUserIsNotPresent() {

        //given
        Project project = Project.builder().name("Test project 1").build();
        when(userRepository.findByName(userDetails.getUsername())).thenReturn(Optional.empty());

        //when
        projectService.saveProject(project);

        //then
        assertNull(project.getUser());

        InOrder inOrder = inOrder(userRepository, projectRepository);

        inOrder.verify(userRepository, times(1)).findByName(userDetails.getUsername());
        inOrder.verify(projectRepository, times(1)).save(project);
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

    //нужно доработать!!!
    @Test
    void updateProject_ifProjectExist() {

        //given
        Project oldProject = Project.builder().name("Test old project").build();
        Project newProject = Project.builder().name("Test new project")
                .description("update")
                .endDate(LocalDateTime.now())
                .build();

        // у newProject должен быть такой же id, как у oldProject
        // т.к. мы не можем установить у newProject id от oldProject, то в данном тесте мы находим по id newProject'a oldProject.
        UUID oldProjectId = newProject.getId();
        doReturn(Optional.of(oldProject)).when(projectRepository).findById(oldProjectId);

        doAnswer(invocation -> {
            Project currentProject = invocation.getArgument(0);
            return currentProject;
        }).when(projectRepository).save(any(Project.class));

        //when
        Project updateProject = projectService.updateProject(newProject);

        //then
        assertEquals(updateProject.getStartDate(), oldProject.getStartDate());
        assertEquals(updateProject.getId(), oldProject.getId());
        assertEquals(updateProject.getUser(), oldProject.getUser());
        assertEquals(updateProject.getTasks(), oldProject.getTasks());

        assertEquals(updateProject.getName(), newProject.getName());
        assertEquals(updateProject.getDescription(), newProject.getDescription());
        assertEquals(updateProject.getEndDate(), newProject.getEndDate());

        InOrder inOrder = inOrder(projectRepository);
        inOrder.verify(projectRepository, times(2)).findById(oldProjectId);
        inOrder.verify(projectRepository, times(1)).save(updateProject);
    }

    @Test
    void updateProject_ifProjectNotExist() {

        //given
        Project project = Project.builder().name("Test project 1").build();
        UUID id = project.getId();
        doReturn(Optional.empty()).when(projectRepository).findById(id);

        //when
        projectService.updateProject(project);

        //then
        InOrder inOrder = inOrder(projectRepository);
        inOrder.verify(projectRepository, times(1)).findById(id);
        inOrder.verify(projectRepository, never()).save(project);

        assertNull(projectService.updateProject(project));
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