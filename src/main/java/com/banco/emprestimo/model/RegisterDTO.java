package com.banco.emprestimo.model;

public record RegisterDTO(String login, String password, UserRole role) {
}