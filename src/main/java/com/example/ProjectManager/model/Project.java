package com.example.ProjectManager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "projects")
@Builder
@AllArgsConstructor
public class Project {

    //добавить валидацию (проверку данных на корректность)

    public Project() {
        id = UUID.randomUUID();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id;
    
    private String name;
    private String description;
    
    private final LocalDateTime startDate = LocalDateTime.now(); //сделать через аннотацию
    private LocalDateTime endDate;

    @ManyToOne
    private User user;

    @OneToMany
    @JoinColumn(name = "project_id")
    private List<Task> tasks;
}
