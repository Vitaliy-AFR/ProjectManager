package com.example.ProjectManager.mapper;

import com.example.ProjectManager.model.User;
import com.example.ProjectManager.model.response.UserDTO;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDTO userToDTO(User user);

}
