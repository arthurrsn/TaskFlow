package br.projeto.taskflow.service;

import br.projeto.taskflow.dto.TaskRequest;
import br.projeto.taskflow.dto.TaskResponse;
import br.projeto.taskflow.model.Task;
import br.projeto.taskflow.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <h1>TaskService</h1>
 * <strong>API Orchestrator</strong>
 * <p>Manages the business logic for 'Task' operations.</p>
 * <p>
 *     It is responsible for orchestrating, validating, and executing functions related to tasks,
 *     interacting with the repository layer for data persistence.
 * </p>
 * @author Arthur Ribeiro
 * @version 0.2.0
 * @since 2025-07-21
 */
@Service
public class TaskService {
    private final TaskRepository taskRepository;

    // Builder
    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    /**
     * <h1>createTask</h1>
     * <strong>Creates a new task.</strong>
     * <p>Receives a DTO with task information. All data will undergo treatment and validation,
     * and will then be stored in the database via the repository. The created task entity is returned.</p>
     *
     * @param taskRequest DTO containing the data for the task to be created.
     * @return An instance of {@link br.projeto.taskflow.dto.TaskResponse} representing the created task.
     */
    public TaskResponse createTask(TaskRequest taskRequest) throws IllegalArgumentException {
        LocalDateTime createAt = LocalDateTime.now();

        String title = taskRequest.title().trim();
        String description = taskRequest.description().trim();
        String status = taskRequest.status();
        String priority = taskRequest.priority();

        // Creating a new task on instance of Task
        Task newTask = new Task(null, title, description, status, priority, createAt);
        // Save task in Data Base
        Task savedTask = taskRepository.save(newTask);

        return convertToTaskResponse(savedTask);
    }


    public List<TaskResponse> searchTask(String search){}
    public TaskResponse updateTask(Long id, TaskRequest taskRequest){}
    public void deleteTask(Long id){}

    /**
     * <h1>convertToTaskResponse</h1>
     * <strong>Convert an object Task</strong>
     * <p>
     *     Maps the fields from the {@link br.projeto.taskflow.model.Task} entity to a
     *     {@link br.projeto.taskflow.dto.TaskResponse} DTO.
     *     This method ensures that only the necessary data is exposed to the API client.
     * </p>
     * @param task The {@link br.projeto.taskflow.model.Task} entity to convert.
     * @return The corresponding {@link br.projeto.taskflow.dto.TaskResponse} DTO.
     */
    private TaskResponse convertToTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getCreatedAt()
        );
    }

}
