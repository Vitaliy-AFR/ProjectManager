package com.example.ProjectManager.service.impl;

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

    @Test
    void findAllTasks() {

        //when
        taskService.findAllTasks();

        //then
        verify(taskRepository, times(1)).findAll();

    }

    @Test
    void findAllTasksForProject() {

        //given
        UUID id = UUID.randomUUID();

        //when
        taskService.findAllTasksForProject(id);

        //then
        verify(taskRepository, times(1)).findByProjectId(id);

    }

    @Test
    void saveTask() {

        //given
        java.util.List<Task> tasks = new ArrayList<>();
        Project project = Project.builder().name("Test project 1").tasks(tasks).build();
        Task task = Task.builder().name("Test task 1").build();
        task.setProjectId(project.getId());
        when(projectRepository.findById(task.getProjectId())).thenReturn(Optional.of(project));
        doAnswer(invocation -> {
            Task currentTask = invocation.getArgument(0);
            return currentTask;
        }).when(taskRepository).save(any(Task.class));

        //when
        taskService.saveTask(task);

        //then
        InOrder inOrder = inOrder(taskRepository, projectRepository);
        inOrder.verify(taskRepository, times(1)).save(task);
        inOrder.verify(projectRepository, times(1)).findById(task.getProjectId());

        assertEquals(task, project.getTasks().get(tasks.size() - 1));

    }

    @Test
    void findById() {

        //given
        UUID id = UUID.randomUUID();

        //when
        taskService.findById(id);

        //then
        verify(taskRepository, times(1)).findById(id);


    }

    @Test
    void updateTask_ifTaskExist() {

        //given
        Task oldTask = Task.builder().name("Test old task").build();
        Task newTask = Task.builder().name("Test new task")
                .description("update")
                .endDate(LocalDateTime.now())
                .build();

        // у newTask должен быть такой же id, как у oldTask
        // т.к. мы не можем установить у newTask id от oldTask, то в данном тесте мы находим по id newTask'a oldTask.
        UUID oldTaskId = newTask.getId();
        doReturn(Optional.of(oldTask)).when(taskRepository).findById(oldTaskId);

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
        inOrder.verify(taskRepository, times(2)).findById(oldTaskId);
        inOrder.verify(taskRepository, times(1)).save(updateTask);

    }

    @Test
    void updateTask_ifTaskNotExist() {

        //given
        Task task = Task.builder()
                .name("Test task")
                .build();
        when(projectRepository.findById(task.getId())).thenReturn(Optional.empty());


        //when
        taskService.updateTask(task);

        //then
        InOrder inOrder = inOrder(taskRepository);
        inOrder.verify(taskRepository, times(1)).findById(task.getId());
        inOrder.verify(taskRepository, never()).save(task);

        assertNull(taskService.updateTask(task));

    }

    @Test
    void deleteTask() {

        //given
        UUID id = UUID.randomUUID();

        //when
        taskService.deleteTask(id);

        //then
        verify(taskRepository, times(1)).deleteById(id);

    }
}