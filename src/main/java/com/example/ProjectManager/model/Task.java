package com.example.ProjectManager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tasks")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
