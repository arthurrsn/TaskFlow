package br.projeto.taskflow.controller;

import br.projeto.taskflow.dto.TaskRequest;
import br.projeto.taskflow.dto.TaskResponse;
import br.projeto.taskflow.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h1>TaskController</h1>
 * <strong>API Request Manager</strong>
 * <p>Acts as the presentation layer, managing HTTP requests for the 'Task' resource.</p>
 * <p>It is responsible for mapping endpoints, receiving request data, and delegating business logic
 * to {@link br.projeto.taskflow.service.TaskService}.</p>
 *
 * @author Arthur Ribeiro
 * @version 0.2.0
 * @since 2025-07-21
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    // Builder
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * <h1>createTask</h1>
     * <strong>Creates a new task.</strong>
     * <p>Receives new task data via a request DTO and delegates the creation to the service.</p>
     *
     * @param taskRequest DTO containing the data for the task to be created.
     * @return An ResponseEntity with Status 201 and return of {@link br.projeto.taskflow.service.TaskService}
     * representing the created task.
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskRequest taskRequest){
        return ResponseEntity.
                status(HttpStatus.CREATED).
                header("Successfully creation").
                body(taskService.createTask(taskRequest)); // Status: 201
    }

    /**
     * <h1>searchTask</h1>
     * <strong>Searches for tasks by a given term.</strong>
     * <p>Receives a search term and delegates the search to the service, which will check task titles and descriptions.</p>
     *
     * <p>TODO: Add search functionality with filters using {@code @RequestParam}.</p>
     *
     * @param search The term used for the search.
     * @return A Response Entity with Status 200 and a list of {@link br.projeto.taskflow.dto.TaskResponse}
     * containing the found tasks.
     */
    @GetMapping("/{search}")
    public ResponseEntity<List<TaskResponse>> searchTask(
            @NotBlank(message = "Search is invalid")
            @PathVariable String search){
        return ResponseEntity.
                status(HttpStatus.OK).
                header("Successfully search.").
                body(taskService.searchTask(search)); // Status: 200
    }

    /**
     * <h1>updateTask</h1>
     * <strong>Updates an existing task.</strong>
     * <p>Receives the task identifier and new data via a request DTO, delegating the update to the service.</p>
     *
     * @param id The unique identifier of the task to be updated.
     * @param taskRequest DTO containing the complete task data for the update.
     * @return A Response Entity with Status 200 and an instance
     * of {@link br.projeto.taskflow.dto.TaskResponse} with the updated task data.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PositiveOrZero(message = "Id is invalid") @PathVariable Long id,
            @Valid @RequestBody TaskRequest taskRequest){
        return ResponseEntity.
                status(HttpStatus.OK).
                header("Successfully update.").
                body(taskService.updateTask(id, taskRequest)); // Status: 200
    }

    /**
     * <h1>deleteTask</h1>
     * <strong>Deletes a task.</strong>
     * <p>Receives the task identifier and delegates the deletion to the service.</p>
     *
     * @param id The unique identifier of the task to be deleted.
     * @return A Status 204 indicating that Delete were successfully.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PositiveOrZero(message = "Id is invalid")
            @PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build(); // Status: 204
    }
}
