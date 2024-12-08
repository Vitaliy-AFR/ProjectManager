package com.example.ProjectManager.service;

import com.example.ProjectManager.Exceptions.NotFoundException;
import com.example.ProjectManager.model.User;
import com.example.demo.model.response.PersonDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(User user);
    List<PersonDTO> findAllUsers();
    Optional<PersonDTO> findByName(String name) throws NotFoundException;
    void deleteUser(String name) throws NotFoundException;
}
