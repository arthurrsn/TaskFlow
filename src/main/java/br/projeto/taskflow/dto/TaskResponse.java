package br.projeto.taskflow.dto;

public record TaskResponse(String user, String title, String description, String status, String priority) {
}
