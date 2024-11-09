package com.example.ProjectManager.controller;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
Всю бизнес логику нужно покрыть тестами
Изучить тесты для контроллеров и для репозиториев (с помощью спринга). Интеграционные тесты.
Для сервисов - юнит тесты (Mockito и JUnit). (Спринг - ограниченный контекст).
 */

@RestController
@RequestMapping("api/v1/projects")
@AllArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Project>> findAllProjects() {
//        return new ResponseEntity<>(service.findAllProjects(), HttpStatus.OK) ;
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.projectService.findAllProjects());
    }

    @PostMapping("save_project")
    public String saveProject(@RequestBody Project project) {
        projectService.saveProject(project);
        return "Проект добавлен";
    }

    @GetMapping("/{id}")
    public Optional<Project> findById(@PathVariable UUID id) {
        return projectService.findById(id);
    }

    @PutMapping("update_project")
    public Project updateProject(@RequestBody Project project) {
        return projectService.updateProject(project);
    }

    @DeleteMapping("delete_project/{id}")
    public String deleteProject(@PathVariable UUID id){
        if (projectService.findById(id).isPresent()){
            projectService.deleteProject(id);
            return "Проект удален";
        } else {
            return "Такого проекта не существует";
        }
    }

}
