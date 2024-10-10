package com.example.ProjectManager.repository;

import com.example.ProjectManager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    default Task updateTask(Task task) {
        if (!findById(task.getId()).isPresent()) return null;
        Task newTask = findById(task.getId()).get();
        newTask.setName(task.getName());
        newTask.setDescription(task.getDescription());
        newTask.setEndDate(task.getEndDate());
        return save(newTask);
    }
}
