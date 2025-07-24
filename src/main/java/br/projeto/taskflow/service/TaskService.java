package br.projeto.taskflow.service;

import br.projeto.taskflow.dto.TaskRequest;
import br.projeto.taskflow.dto.TaskResponse;
import br.projeto.taskflow.model.Task;
import br.projeto.taskflow.repository.TaskRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h1>TaskService</h1>
 * <strong>API Orchestrator</strong>
 * <p>Manages the business logic for 'Task' operations.</p>
 * <p>
 *     It is responsible for orchestrating, validating, and executing functions related to tasks,
 *     interacting with the repository layer for data persistence.
 * </p>
 * @author Arthur Ribeiro
 * @version 0.7.0
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
     * <h1>listAllTasks</h1>
     * <strong>Lists all tasks.</strong>
     * <p>Retrieves all tasks from the database.</p>
     * <p>Each retrieved entity is then converted to a DTO for response.</p>
     * @return A {@link java.util.List} of {@link br.projeto.taskflow.dto.TaskResponse} containing all tasks.
     */
    public List<TaskResponse> listAllTasks(){
        List<Task> tasksRetrieved = taskRepository.findAll();
        return tasksRetrieved.stream()
                .map(this::convertToTaskResponse)
                .toList();
    }

    /**
     * <h1>createTask</h1>
     * <strong>Creates a new task.</strong>
     * <p>Receives a DTO with task information. All data will undergo treatment and validation,
     * and will then be stored in the database via the repository. The created task entity is returned.</p>
     *
     * @param taskRequest DTO containing the data for the task to be created.
     * @return An instance of {@link TaskResponse} representing the created task.
     */
    public TaskResponse createTask(TaskRequest taskRequest) {

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

    /**
     * <h1>searchTask</h1>
     * <strong>Searches for tasks based on a given term.</strong>
     * <p>Receives a search term and splits it into words, looking for tasks that contain these words
     * in their title or description. It removes duplicates and returns a list of DTOs.</p>
     * <p>Ensures efficient searching by utilizing a {@code LinkedHashSet} to prevent duplicate results.</p>
     *
     * @param search The term (or space-separated terms) to be used for the search.
     * @return A {@link List} of {@link TaskResponse} containing the found tasks.
     */
    public List<TaskResponse> searchTask(String search){
        String[] splitSearch = search.trim().split("\\s+");
        Set<Task> itensRetrieved = new LinkedHashSet<>();

        if (search.isEmpty() || search.trim().isBlank()){
            return List.of(); // return Empty List
        }

        for (String word : splitSearch) {
            if (word.isEmpty()){
                continue;
            }
            List<Task> searchItensInDataBase = taskRepository.findByTitleContainingOrDescriptionContaining(word, word);
            itensRetrieved.addAll(searchItensInDataBase);

        }

        return itensRetrieved.stream().
                map(this::convertToTaskResponse).
                collect(Collectors.toList());
    }

    /**
     * <h1>updateTask</h1>
     * <strong>Updates an existing task.</strong>
     * <p>Receives the unique identifier of the task and new data via a request DTO.</p>
     * <p>The existing task is retrieved, updated with the provided information, and then persisted in the database.</p>
     *
     * @param id Identifier for the task to be updated in the database.
     * @param taskRequest DTO containing the new information for the task.
     * @return An instance of {@link TaskResponse} with the updated task data.
     * @throws NoSuchElementException If the task with the given ID is not found.
     */
    public TaskResponse updateTask(Long id, TaskRequest taskRequest) throws NoSuchElementException {
        Task oldTask = taskRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Task not found with ID: " + id));

        oldTask.setTitle(taskRequest.title().trim());
        oldTask.setDescription(taskRequest.description().trim());
        oldTask.setStatus(taskRequest.status());
        oldTask.setPriority(taskRequest.priority());

        Task updatedTask = taskRepository.save(oldTask);

        return convertToTaskResponse(updatedTask);
    }

    /**
     * <h1>deleteTask</h1>
     * <strong>Deletes a Task by ID.</strong>
     * <p>
     *     Verifies if the task exists in the database by its unique identifier. If the task is not found,
     *     a {@link NoSuchElementException} is thrown. Otherwise, the task is deleted from the database.
     * </p>
     * @param id Unique identifier for the task to be deleted.
     * @throws NoSuchElementException If the task with the given ID is not found in the database.
     */
    public void deleteTask(@PositiveOrZero @NotNull Long id){
        if (!taskRepository.existsById(id)){
            throw new NoSuchElementException("Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    /**
     * <h1>convertToTaskResponse</h1>
     * <strong>Convert an object Task</strong>
     * <p>
     *     Maps the fields from the {@link Task} entity to a
     *     {@link TaskResponse} DTO.
     *     This method ensures that only the necessary data is exposed to the API client.
     * </p>
     * @param task The {@link Task} entity to convert.
     * @return The corresponding {@link TaskResponse} DTO.
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

