package br.projeto.taskflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <h1>Task</h1>
 * <strong>Domain Entity for Tasks.</strong>
 * <p>Represents a task in the system and is mapped to a table in the database.</p>
 * <p>Contains the essential attributes of a task and is managed by JPA for persistence.</p>
 *
 * @author Arthur Ribeiro
 * @version 0.2.0
 * @since 2025-07-21
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;
    private String priority;

    private LocalDateTime createdAt;
}