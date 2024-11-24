package com.example.ProjectManager.controller;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.Exceptions.NotFoundException;
import com.example.ProjectManager.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
(Спринг - ограниченный контекст).
 */

@RestController
@RequestMapping("api/v1/projects")
@AllArgsConstructor
@Slf4j
public class ProjectController {

    private static final String PROJECT_ADDED = "Проект добавлен";
    private static final String PROJECT_DELETED = "Проект удален";
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<Project>> findAllProjects() {
        log.info("Показываем все проекты для профиля Admin или проекты для конкретного User");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.projectService.findAllProjects());
    }

    @PostMapping("save_project")
    public ResponseEntity<String> saveProject(@RequestBody Project project) {
        projectService.saveProject(project);
        log.info("saveProject with name: {}", project.getName());
        return new ResponseEntity<>(PROJECT_ADDED, HttpStatus.CREATED) ;

    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> findById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok().body(projectService.findById(id).get());
    }

    @PutMapping("update_project")
    public ResponseEntity<Project> updateProject(@RequestBody Project project) throws NotFoundException {
        return ResponseEntity.ok()
                .body(projectService.updateProject(project));
    }

    @DeleteMapping("delete_project/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable UUID id) throws NotFoundException{
        projectService.deleteProject(id);
        return new ResponseEntity<>(PROJECT_DELETED, HttpStatus.OK);

    }

}
