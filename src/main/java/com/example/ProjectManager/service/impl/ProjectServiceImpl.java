package com.example.ProjectManager.service.impl;

import com.example.ProjectManager.config.MyUserDetails;
import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.model.User;
import com.example.ProjectManager.repository.ProjectRepository;
import com.example.ProjectManager.repository.TaskRepository;
import com.example.ProjectManager.repository.UserRepository;
import com.example.ProjectManager.Exceptions.NotFoundException;
import com.example.ProjectManager.service.ProjectService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Primary
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository; //переименовать в проджект репозитори
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private static final String NAME_ADMIN = "admin";
    private static final String PROJECT_NOT_EXIST = "Такого проекта не существует";

    @Override
    public List<Project> findAllProjects() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Optional<User> currentUser = userRepository.findByName(userDetails.getUsername());
        if (currentUser.get().getName().equals(NAME_ADMIN)) {
            return projectRepository.findAll();
        } else {
            return projectRepository.findAllByUser(currentUser.get());
        }
    }

    @Override
    public Project saveProject(Project project) {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Optional<User> currentUser = userRepository.findByName(userDetails.getUsername());
        currentUser.ifPresent(project::setUser);
        Project correctProject;
        //теперь если передан project без id, то randomId создается тут (раньше было в классе сущности)
        if (project.getId() == null) {
            correctProject = Project.builder()
                    .id(UUID.randomUUID())
                    .name(project.getName())
                    .description(project.getDescription())
                    .endDate(project.getEndDate())
                    .user(project.getUser())
                    .tasks(project.getTasks())
                    .build();
        } else {
            correctProject = project;
        }
        return projectRepository.save(correctProject);
    }

    @Override
    public Optional<Project> findById(UUID id) throws NotFoundException {
        if (projectRepository.findById(id).isEmpty()) {
            throw new NotFoundException(PROJECT_NOT_EXIST);
        }
        return projectRepository.findById(id);
    }

    @Override //изучить как работает hibernate
    public Project updateProject(Project project) throws NotFoundException {
        if (projectRepository.findById(project.getId()).isEmpty()) {
            throw new NotFoundException(PROJECT_NOT_EXIST);
        }
        Project newProject = projectRepository.findById(project.getId()).get(); //hibernate отслеживает new project
        newProject.setName(project.getName());
        newProject.setDescription(project.getDescription());
        newProject.setEndDate(project.getEndDate());
        return projectRepository.save(newProject);
    }

    @Override
    @Transactional //изучить как работает
    public void deleteProject(UUID id) throws NotFoundException {
        if (findById(id).isEmpty()) {
            throw new NotFoundException(PROJECT_NOT_EXIST);
        }
        taskRepository.deleteByProjectId(id);
        projectRepository.deleteById(id);
    }
}
