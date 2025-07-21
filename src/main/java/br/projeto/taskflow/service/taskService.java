package br.projeto.taskflow.service;

import br.projeto.taskflow.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class taskService {
    private final TaskRepository taskRepository;

    // Builder
    @Autowired
    public taskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }
}
