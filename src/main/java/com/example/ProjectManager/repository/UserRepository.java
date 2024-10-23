package com.example.ProjectManager.repository;

import com.example.ProjectManager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByName(String name);
    void deleteByName(String name);
}
