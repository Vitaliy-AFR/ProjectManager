package com.example.ProjectManager.mapper;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.model.Task;
import com.example.ProjectManager.model.User;
import com.example.ProjectManager.model.response.ProjectDTO;
import com.example.ProjectManager.model.response.TaskDTO;
import com.example.ProjectManager.model.response.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "tasks", qualifiedByName = "fromProjectTasksToTaskDTOList", source = "tasks")
    @Mapping(target = "user", qualifiedByName = "fromProjectUserToUserDTO", source = "user")
    ProjectDTO projectToDTO(Project project);

    @Named("fromProjectTasksToTaskDTOList")
    default List<TaskDTO> fromProjectTasksToTaskDTOList(List<Task> tasks){
        return toTaskDTOList(tasks);
    }

    @Named("fromProjectUserToUserDTO")
    default UserDTO fromProjectUserToUserDTO(User user) {
        return userToUserDTO(user);
    }

    List<TaskDTO> toTaskDTOList(List<Task> tasks);

    UserDTO userToUserDTO(User user);

    List<ProjectDTO> toProjectDTOList(List<Project> projects);




}
