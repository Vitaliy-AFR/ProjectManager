package com.example.ProjectManager.repository;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.model.Task;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class InMemoryTaskDAO {

    private final List<Task> TASKS = new ArrayList<>();
    private ProjectRepository projectRepository;

    public boolean projectNotExist(UUID projectId){
        return projectRepository.findById(projectId).isPresent() ? false : true;
    }

    public List<Task> findAllTasks() {
        return TASKS;
    }

    public List<Task> findAllTasksForProject(UUID projectId) {
        if (projectNotExist(projectId)) return null;
        return TASKS.stream().filter(v -> v.getProjectId().equals(projectId)).toList();
    }

    public Task saveTask(Task task) {
        if (projectNotExist(task.getProjectId())) return null;
        task.setProjectId(task.getProjectId());
        TASKS.add(task);
        Project project = projectRepository.findById(task.getProjectId()).get();
        project.getTasks().add(task);
        projectRepository.save(project);
        return task;
    }

    public Task findById(UUID id) {
        return TASKS.stream().filter(v -> v.getId().equals(id)).findAny().orElse(null);
    }

    private void updateTaskInProject(Task task) {
        Project project = projectRepository.findById(task.getProjectId()).get();
        int i = 0;
        for (int j = 0; j < project.getTasks().size(); j++) {
            if (project.getTasks().get(j).getId().equals(task.getId())) i = j;
        }
        project.getTasks().get(i).setName(task.getName());
        project.getTasks().get(i).setDescription(task.getDescription());
        project.getTasks().get(i).setEndDate(task.getEndDate());
        projectRepository.updateProject(project);
    }

    public Task updateTask(Task task) {
        int i = TASKS.indexOf(findById(task.getId()));
        if (TASKS.contains(findById(task.getId()))) {
            TASKS.get(i).setName(task.getName());
            TASKS.get(i).setDescription(task.getDescription());
            TASKS.get(i).setEndDate(task.getEndDate());
            updateTaskInProject(TASKS.get(i));
            return TASKS.get(i);
        }
        return null;
    }

    public void deleteTask(UUID id) {
        var task = findById(id);
        if (task != null) {
            TASKS.remove(task);
        }
    }

}
