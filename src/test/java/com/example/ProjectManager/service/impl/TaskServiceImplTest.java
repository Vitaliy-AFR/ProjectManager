package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.Exceptions.NotFoundException;
import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.model.Task;
import com.example.ProjectManager.repository.ProjectRepository;
import com.example.ProjectManager.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {
                TaskServiceImpl.class
        }
)
class TaskServiceImplTest {

    private static final String PROJECT_NOT_EXIST = "Такого проекта не существует";
    private static final String TASK_NOT_EXIST = "Такой задачи не существует";
    @Autowired
    TaskServiceImpl taskService;

    @MockBean
    TaskRepository taskRepository;

    @MockBean
    ProjectRepository projectRepository;

    @Test
    void projectNotExist_whenProjectExist() {

        //given
        Project project = Project.builder().name("Test project 1").build();
        UUID id = project.getId();
        when(projectRepository.findById(id)).thenReturn(Optional.of(project));

        //when
        taskService.projectNotExist(id);

        //then
        verify(projectRepository, times(1)).findById(id);
        assertFalse(taskService.projectNotExist(id));

    }

    @Test
    void projectNotExist_whenProjectNotExist() {

        //given
        UUID id = mock(UUID.class);
        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        //when
        taskService.projectNotExist(id);

        //then
        verify(projectRepository, times(1)).findById(id);
        assertTrue(taskService.projectNotExist(id));

    }

    //@Test
    void findAllTasks() {

        //when
        taskService.findAllTasks();

        //then
        verify(taskRepository, times(1)).findAll();

    }

    @Test
    void findAllTasksForProject_ifProjectExist() throws NotFoundException {

        //given
        UUID projectId = UUID.randomUUID();
        Project project = Project.builder()
                .id(projectId)
                .name("Test project")
                .build();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        //when
        taskService.findAllTasksForProject(projectId);

        //then
        verify(taskRepository, times(1)).findByProjectId(projectId);

    }

    @Test
    void findAllTasksForProject_ifProjectNotExist() throws NotFoundException {

        //given
        UUID projectId = UUID.randomUUID();
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(NotFoundException.class, () -> {
            taskService.findAllTasksForProject(projectId);
        });


        //then
        assertEquals(PROJECT_NOT_EXIST, exception.getMessage());
        verify(taskRepository, never()).findByProjectId(projectId);

    }

    @Test
    void saveTask_ifProjectExist() throws NotFoundException {

        //given
        java.util.List<Task> tasks = new ArrayList<>();
        UUID projectId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        Project project = Project.builder()
                .id(projectId)
                .name("Test project 1")
                .tasks(tasks)
                .build();
        Task task = Task.builder()
                .id(taskId)
                .name("Test task 1")
                .projectId(projectId)
                .build();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        doAnswer(invocation -> {
            Task currentTask = invocation.getArgument(0);
            return currentTask;
        }).when(taskRepository).save(any(Task.class));

        //when
        taskService.saveTask(task);

        //then
        InOrder inOrder = inOrder(taskRepository, projectRepository);
        inOrder.verify(projectRepository, times(2)).findById(projectId);
        inOrder.verify(taskRepository, times(1)).save(task);


        assertEquals(task, project.getTasks().get(tasks.size() - 1));

    }

    @Test
    void saveTask_ifProjectNotExist() throws NotFoundException {

        //given
        java.util.List<Task> tasks = new ArrayList<>();
        UUID projectId = UUID.randomUUID();
        UUID taskId = UUID.randomUUID();
        Project project = Project.builder()
                .id(projectId)
                .name("Test project 1")
                .tasks(tasks)
                .build();
        Task task = Task.builder()
                .id(taskId)
                .name("Test task 1")
                .projectId(projectId)
                .build();
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(NotFoundException.class, () -> {
            taskService.saveTask(task);
        });


        //then
        InOrder inOrder = inOrder(taskRepository, projectRepository);
        inOrder.verify(projectRepository, times(1)).findById(projectId);
        inOrder.verify(taskRepository, never()).save(task);


        assertEquals(PROJECT_NOT_EXIST, exception.getMessage());

    }

    @Test
    void findById_ifTaskExist() throws NotFoundException {

        //given
        UUID id = UUID.randomUUID();
        Task task = Task.builder()
                .id(id)
                .name("Test task")
                .build();
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        //when
        taskService.findById(id);

        //then
        verify(taskRepository, times(2)).findById(id);
    }

    @Test
    void findById_ifTaskNotExist() throws NotFoundException {

        //given
        UUID id = UUID.randomUUID();
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(NotFoundException.class, () -> {
            taskService.findById(id);
        });


        //then
        assertEquals(TASK_NOT_EXIST, exception.getMessage());
        verify(taskRepository, times(1)).findById(id);
    }

    @Test
    void updateTask_ifTaskExist() throws NotFoundException {

        //given
        UUID id = UUID.randomUUID();
        Task oldTask = Task.builder()
                .id(id)
                .name("Test old task")
                .build();
        Task newTask = Task.builder()
                .name("Test new task")
                .id(id)
                .description("update")
                .endDate(LocalDateTime.now())
                .build();


        doReturn(Optional.of(oldTask)).when(taskRepository).findById(id);

        doAnswer(invocationOnMock -> {
            Task currentTask = invocationOnMock.getArgument(0);
            return currentTask;
        }).when(taskRepository).save(any(Task.class));

        //when
        Task updateTask = taskService.updateTask(newTask);

        //then
        assertEquals(oldTask.getStartDate(), updateTask.getStartDate());
        assertEquals(oldTask.getId(), updateTask.getId());
        assertEquals(oldTask.getProjectId(), updateTask.getProjectId());

        assertEquals(newTask.getName(), updateTask.getName());
        assertEquals(newTask.getDescription(), updateTask.getDescription());
        assertEquals(newTask.getEndDate(), newTask.getEndDate());

        InOrder inOrder = inOrder(taskRepository);
        inOrder.verify(taskRepository, times(2)).findById(id);
        inOrder.verify(taskRepository, times(1)).save(updateTask);

    }

    @Test
    void updateTask_ifTaskNotExist() throws NotFoundException {

        //given
        Task task = Task.builder()
                .id(UUID.randomUUID())
                .name("Test task")
                .build();
        when(projectRepository.findById(task.getId())).thenReturn(Optional.empty());


        //when
        Exception exception = assertThrows(NotFoundException.class, () -> {
            taskService.updateTask(task);
        });


        //then
        InOrder inOrder = inOrder(taskRepository);
        inOrder.verify(taskRepository, times(1)).findById(task.getId());
        inOrder.verify(taskRepository, never()).save(task);

        assertEquals(TASK_NOT_EXIST, exception.getMessage());

    }

    @Test
    void deleteTask_ifTaskExist() throws NotFoundException {

        //given
        UUID id = UUID.randomUUID();
        Task task = Task.builder()
                .id(id)
                .name("Test task")
                .build();
        when(taskRepository.findById(id)).thenReturn(Optional.of(task));

        //when
        taskService.deleteTask(id);

        //then
        verify(taskRepository, times( 1)).findById(id);
        verify(taskRepository, times(1)).deleteById(id);

    }

    @Test
    void deleteTask_ifTaskNotExist() throws NotFoundException {

        //given
        UUID id = UUID.randomUUID();
        when(taskRepository.findById(id)).thenReturn(Optional.empty());

        //when
        Exception exception = assertThrows(NotFoundException.class, () -> {
            taskService.deleteTask(id);
        });

        //then
        verify(taskRepository, times( 1)).findById(id);
        verify(taskRepository, never()).deleteById(id);

        assertEquals(TASK_NOT_EXIST, exception.getMessage());

    }
}