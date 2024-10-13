package com.example.ProjectManager.repository;

import com.example.ProjectManager.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<MyUser, UUID> {

    Optional<MyUser> findByName(String username);
}
