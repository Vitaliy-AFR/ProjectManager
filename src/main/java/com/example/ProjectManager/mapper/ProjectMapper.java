package com.example.ProjectManager.mapper;

import com.example.ProjectManager.model.Project;
import com.example.ProjectManager.model.response.ProjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(target = "user", source = "java()")
    @Mapping(target = "tasks", source = "java()")
    ProjectDTO projectToDTO(Project project);
}
