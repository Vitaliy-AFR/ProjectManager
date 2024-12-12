package com.example.ProjectManager.controller;

//import com.example.ProjectManager.client.PersonFeignClient;

import com.example.ProjectManager.Exceptions.NotFoundException;
import com.example.ProjectManager.client.PersonFeignClient;
import com.example.ProjectManager.mapper.ProjectMapper;
import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.model.response.ProjectDTO;
import com.example.ProjectManager.service.MessageSender;
import com.example.ProjectManager.service.ProjectService;
import com.example.demo.model.response.PersonDTO;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    private static final String FIND_ALL_PROJECTS_FROM_USER = "Показываем все проекты для профиля Admin или проекты для конкретного User";
    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final PersonFeignClient personFeignClient;
    private final MessageSender messageSender;

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> findAllProjects() {
        log.info(FIND_ALL_PROJECTS_FROM_USER);
        messageSender.send(FIND_ALL_PROJECTS_FROM_USER);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(projectMapper.toProjectDTOList(projectService.findAllProjects()));
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<PersonDTO> findPersonById(@PathVariable @Pattern(regexp = "^[A-Za-z0-9]{4,10}") String id) {
        return personFeignClient.getPerson(id);
    }

    @PostMapping("save_project")
    public ResponseEntity<String> saveProject(@RequestBody Project project) {
        projectService.saveProject(project);
        log.info("saveProject with name: {}", project.getName());
        return new ResponseEntity<>(PROJECT_ADDED, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> findById(@PathVariable UUID id) throws NotFoundException {
        return ResponseEntity.ok().body(projectMapper.projectToDTO(projectService.findById(id).get()));
    }

    @PutMapping("update_project")
    public ResponseEntity<ProjectDTO> updateProject(@RequestBody Project project) throws NotFoundException {
        return ResponseEntity.ok()
                .body(projectMapper.projectToDTO(projectService.updateProject(project)));
    }

    @DeleteMapping("delete_project/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable UUID id) throws NotFoundException {
        projectService.deleteProject(id);
        return new ResponseEntity<>(PROJECT_DELETED, HttpStatus.OK);

    }

}
