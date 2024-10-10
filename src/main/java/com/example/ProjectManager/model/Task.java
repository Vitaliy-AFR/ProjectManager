package com.example.ProjectManager.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id = UUID.randomUUID();

    @Column(name = "project_id")
    private UUID projectId;

    private String name;
    private String description;
    private final LocalDateTime startDate = LocalDateTime.now();
    private LocalDateTime endDate;



}
