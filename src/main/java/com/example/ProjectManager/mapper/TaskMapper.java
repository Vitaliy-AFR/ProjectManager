package com.example.ProjectManager.mapper;

import com.example.ProjectManager.model.Task;
import com.example.ProjectManager.model.response.TaskDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDTO taskToDTO(Task task);
}
