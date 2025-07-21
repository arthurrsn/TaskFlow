package br.projeto.taskflow.dto;

public record TaskRequest(String user, String title, String description, String status, String priority) {
}
