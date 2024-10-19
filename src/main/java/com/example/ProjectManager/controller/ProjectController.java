package com.example.ProjectManager.controller;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    //Изучить как использовать сервис, тот который не праймари. Но аннотацию не менять.

    private final ProjectService service;

    @GetMapping
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Project> findAllProjects() {
        return service.findAllProjects();
    }

    @PostMapping("save_project")
    public String saveProject(@RequestBody Project project) {
        service.saveProject(project);
        return "Проект добавлен";
    }

    @GetMapping("/{id}")
    public Optional<Project> findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PutMapping("update_project")
    public Project updateProject(@RequestBody Project project) {
        return service.updateProject(project);
    }

    @DeleteMapping("delete_project/{id}")
    public String deleteProject(@PathVariable UUID id){
        if (service.findById(id).isPresent()){
            service.deleteProject(id);
            return "Проект удален";
        } else {
            return "Такого проекта не существует";
        }
    }

}
