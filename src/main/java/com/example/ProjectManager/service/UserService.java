package com.example.ProjectManager.service;

import com.example.ProjectManager.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void addUser(User user);
    List<User> findAllUsers();
    Optional<User> findByName(String name);
    void deleteUser(String name);
}
