package com.example.ProjectManager.mapper;

import com.example.ProjectManager.model.Task;
import com.example.ProjectManager.model.response.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {

    TaskDTO taskToDTO(Task task);

    List<TaskDTO> toTaskDTOList(List<Task> tasks);
}
