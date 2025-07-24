package br.projeto.taskflow.dto;

import java.time.LocalDateTime;

/**
 * <h1>TaskResponse</h1>
 * <strong>DTO for Task Responses.</strong>
 * <p>Represents the output data of a task to be returned by the API.</p>
 * <p>Contains the essential attributes of the task, including the generated ID and creation date.</p>
 *
 * @param id The unique identifier of the task.
 * @param title The title of the task.
 * @param description The detailed description of the task.
 * @param status The current status of the task.
 * @param priority The priority of the task.
 * @param createAt The creation date and time of the task.
 */
public record TaskResponse(
        Long id,
        String title,
        String description,
        String status,
        String priority,
        LocalDateTime createAt
) {
}