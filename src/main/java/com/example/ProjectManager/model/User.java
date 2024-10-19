package com.example.ProjectManager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

//Сделать авторизацию с помощбю спринга

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id = UUID.randomUUID();

        @Column(unique = true)
//    @Id
    private String name;
    private String password;
    private String roles;
}
