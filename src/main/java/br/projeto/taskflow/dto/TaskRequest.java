package br.projeto.taskflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * <h1>TaskRequest</h1>
 * <strong>DTO for Task Creation/Update Requests.</strong>
 * <p>Represents the input data for creating or updating a task via the API.</p>
 * <p>Includes basic validations to ensure the integrity of received data.</p>
 *
 * @param title The title of the task. Mandatory field, cannot be blank.
 * @param description The detailed description of the task. Mandatory field, cannot be blank.
 * @param status The current status of the task (e.g., "PENDING", "COMPLETED"). Mandatory field, cannot be blank.
 * @param priority The priority of the task (e.g., "LOW", "MEDIUM", "HIGH"). Mandatory field, cannot be blank.
 */
public record TaskRequest(
        @NotBlank(message = "Title is mandatory.") String title,
        @NotBlank(message = "Description is mandatory.") String description,

        @NotBlank(message = "Status is mandatory.")
        @Pattern(regexp = "PENDING|COMPLETED|IN_PROGRESS", message = "Status must be PENDING, COMPLETED or IN_PROGRESS")
        String status,

        @NotBlank(message = "Priority is mandatory.")
        @Pattern(regexp = "LOW|MEDIUM|HIGH", message = "Priority must be LOW, MEDIUM or HIGH")
        String priority
) {
}