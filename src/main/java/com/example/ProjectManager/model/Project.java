package com.example.ProjectManager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "projects")
//@Builder
//@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID id = UUID.randomUUID();

    private String name;
    private String description;
    private final LocalDateTime startDate = LocalDateTime.now();
    private LocalDateTime endDate;

//    @ManyToOne
//    private MyUser user;

    @OneToMany(cascade = CascadeType.ALL) //убрать каскад, сделать самостоятельно удаление, использовать @Transactional
    @JoinColumn(name = "project_id")
    private List<Task> tasks;
}
