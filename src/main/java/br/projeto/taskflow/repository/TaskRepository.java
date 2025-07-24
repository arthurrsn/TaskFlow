package br.projeto.taskflow.repository;

import br.projeto.taskflow.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <h1>TaskRepository</h1>
 * <strong>Data Access Layer for Task Entity.</strong>
 * <p>
 * This interface acts as the Repository layer, responsible for direct interaction with the database
 * for the {@link br.projeto.taskflow.model.Task} entity.
 * </p>
 * <p>
 * It extends {@link org.springframework.data.jpa.repository.JpaRepository},
 * inheriting standard CRUD (Create, Read, Update, Delete) operations.
 * Custom query methods are defined here, which Spring Data JPA implements automatically.
 * </p>
 * @author Arthur Ribeiro
 * @version 0.3.0
 * @since 2025-07-22
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Retrieves a list of tasks where the title or description contains the specified keyword.
     * This method is equivalent to a SQL query like:
     * {@code SELECT * FROM task WHERE title LIKE %keyword% OR description LIKE %keyword%}
     *
     * @param keyword1 The keyword to search for within the task's title.
     * @param keyword2 The keyword to search for within the task's description.
     * @return A list of {@link Task} entities matching the search criteria.
     */
    List<Task> findByTitleContainingOrDescriptionContaining(String keyword1, String keyword2);

}