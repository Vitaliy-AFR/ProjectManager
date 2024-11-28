package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.Config.MyUserDetails;
import com.example.ProjectManager.Exceptions.NotFoundException;
import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.model.User;
import com.example.ProjectManager.repository.ProjectRepository;
import com.example.ProjectManager.repository.TaskRepository;
import com.example.ProjectManager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
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
import java.util.stream.Stream;

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
    private static final String PROJECT_NOT_EXIST = "Такого проекта не существует";


    @BeforeEach
    void setUp() {
        when(securityContext.getAuthentication()).thenReturn(authentication); // Привязка контекста к объекту
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.setContext(securityContext); // Размещаем dummy-объекты в контексте
    }


    @Test
    void findAllProjects_forRoleAdmin_findAll() {

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
    void findAllProjects_forAnotherRole_findAllByUser() {

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

    static Stream<UUID> uuidProviderFactory(){
        return Stream.of(UUID.randomUUID());
    }

    @ParameterizedTest
    @MethodSource("uuidProviderFactory")
    void saveProject_ifUserIsPresent(UUID id) {

        //given
        Project project = Project.builder()
                .id(id)
                .name("Test project 1")
                .build();
        User user = User.builder().name(USER_NAME).build();
        when(userDetails.getUsername()).thenReturn(USER_NAME);
        when(userRepository.findByName(USER_NAME)).thenReturn(Optional.of(user));
        doAnswer(invocation -> {
            Project currentProject = invocation.getArgument(0);
            return currentProject;
        }).when(projectRepository).save(any(Project.class));

        //when
        Project savedProject = projectService.saveProject(project);

        //then
        assertEquals(project.getUser(), savedProject.getUser());
        assertInstanceOf(UUID.class, savedProject.getId());
        assertEquals(project.getName(), savedProject.getName());
        assertEquals(project.getDescription(), savedProject.getDescription());
        assertInstanceOf(LocalDateTime.class, savedProject.getStartDate());
        assertEquals(project.getEndDate(), savedProject.getEndDate());
        assertEquals(project.getUser(), savedProject.getUser());
        assertEquals(project.getTasks(), savedProject.getTasks());

        InOrder inOrder = inOrder(userRepository, projectRepository);

        inOrder.verify(userRepository, times(1)).findByName(USER_NAME);
        inOrder.verify(projectRepository, times(1)).save(project);
    }

    @ParameterizedTest
    @MethodSource("uuidProviderFactory")
    void saveProject_ifUserIsNotPresent(UUID id) {

        //given
        when(projectRepository.findById(null)).thenReturn(Optional.empty());
        Project project = Project.builder()
                .id(id)
                .name("Test project 1")
                .build();
        when(userRepository.findByName(userDetails.getUsername())).thenReturn(Optional.empty());
        doAnswer(invocation -> {
            Project currentProject = invocation.getArgument(0);
            return currentProject;
        }).when(projectRepository).save(any(Project.class));

        //when
        Project savedProject = projectService.saveProject(project);


        //then
        assertNull(savedProject.getUser());
        assertInstanceOf(UUID.class, savedProject.getId());
        assertEquals(project.getName(), savedProject.getName());
        assertEquals(project.getDescription(), savedProject.getDescription());
        assertInstanceOf(LocalDateTime.class, savedProject.getStartDate());
        assertEquals(project.getEndDate(), savedProject.getEndDate());
        assertEquals(project.getUser(), savedProject.getUser());
        assertEquals(project.getTasks(), savedProject.getTasks());



        InOrder inOrder = inOrder(userRepository, projectRepository);

        inOrder.verify(userRepository, times(1)).findByName(userDetails.getUsername());
        inOrder.verify(projectRepository, times(1)).save(project);
    }

    @Test
    void findById_whenProjectExist() throws NotFoundException {

        //given
        UUID id = UUID.randomUUID();
        Project project = Project.builder()
                .id(id)
                .name("Test project")
                .build();
        when(projectRepository.findById(id)).thenReturn(Optional.of(project));

        //when
        projectService.findById(id);

        //then
        verify(projectRepository, times(2)).findById(id);

    }

    @Test
    void findById_whenProjectNotExist() throws NotFoundException {

        //given
        UUID id = UUID.randomUUID();
        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(NotFoundException.class, () -> {
            projectService.findById(id);
        });

        //then
        verify(projectRepository, times(1)).findById(id);
        assertEquals(PROJECT_NOT_EXIST, exception.getMessage());
    }

    //нужно доработать!!!
    @Test
    void updateProject_ifProjectExist() throws NotFoundException {

        //given
        Project oldProject = Project.builder()
                .id(UUID.randomUUID())
                .name("Test old project")
                .build();
        Project newProject = Project.builder()
                .id(oldProject.getId())
                .name("Test new project")
                .description("update")
                .endDate(LocalDateTime.now())
                .build();

        UUID id = oldProject.getId();
        doReturn(Optional.of(oldProject)).when(projectRepository).findById(id);

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
        inOrder.verify(projectRepository, times(2)).findById(id);
        inOrder.verify(projectRepository, times(1)).save(updateProject);
    }

    @Test
    void updateProject_ifProjectNotExist()  throws NotFoundException {

        //given
        Project project = Project.builder().name("Test project 1").build();
        UUID id = project.getId();
        doReturn(Optional.empty()).when(projectRepository).findById(id);

        //when
        Exception exception = assertThrows(NotFoundException.class, () -> {
            projectService.updateProject(project);
        });

        //then
        InOrder inOrder = inOrder(projectRepository);
        inOrder.verify(projectRepository, times(1)).findById(id);
        inOrder.verify(projectRepository, never()).save(project);

        assertEquals(PROJECT_NOT_EXIST, exception.getMessage());
    }

    @ParameterizedTest (name = "{index} - Project exist = {0}")
    @ValueSource(booleans = {true, false})
    void deleteProject(boolean projectExist) throws NotFoundException {

        //given
        UUID id = UUID.randomUUID();
        Project project = Project.builder()
                .id(id)
                .name("Test project 1")
                .build();
        Exception exception = null;
        if (projectExist) {
            //when
            when(projectRepository.findById(id)).thenReturn(Optional.of(project));
            projectService.deleteProject(id);

            //then
            InOrder inOrder = inOrder(taskRepository, projectRepository);
            inOrder.verify(taskRepository, times(1)).deleteByProjectId(id);
            inOrder.verify(projectRepository, times(1)).deleteById(id);
        } else {
            //when
            when(projectRepository.findById(id)).thenReturn(Optional.empty());
            exception = assertThrows(NotFoundException.class, () -> {
                projectService.deleteProject(id);
            });

            //then
            assertEquals(PROJECT_NOT_EXIST, exception.getMessage());
            verify(taskRepository, never()).deleteByProjectId(id);
            verify(projectRepository, never()).deleteById(id);
        }
    }
}