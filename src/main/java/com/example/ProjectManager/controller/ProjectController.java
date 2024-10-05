package com.example.ProjectManager.controller;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService service;

    @GetMapping
    public Map<UUID, Project> findAllProjects() {
        return service.findAllProjects();
    }

    @PostMapping("save_project")
    public String saveProject(@RequestBody Project project) {
        service.saveProject(project);
        return "Проект успешно добавлен";
    }

    @GetMapping("/{id}")
    public Project findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PutMapping("update_project")
    public Project updateProject(@RequestBody Project project) {
        return service.updateProject(project);
    }

    @DeleteMapping("delete_project/{id}")
    public void deleteProject(@PathVariable UUID id){
        service.deleteProject(id);
    }

}
