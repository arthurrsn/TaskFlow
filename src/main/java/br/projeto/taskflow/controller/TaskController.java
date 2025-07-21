package br.projeto.taskflow.controller;

import br.projeto.taskflow.service.taskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class TaskController {
    private final taskService taskService;

    // Builder
    @Autowired
    public TaskController(taskService taskService) {
        this.taskService = taskService;
    }
}
