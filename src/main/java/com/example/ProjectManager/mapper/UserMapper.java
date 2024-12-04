package com.example.ProjectManager.mapper;

import com.example.ProjectManager.model.User;
import com.example.ProjectManager.model.response.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "name", source = "name")
    UserDTO userToDTO(User user);

    List<UserDTO> toUserDTOList(List<User> users);

}
