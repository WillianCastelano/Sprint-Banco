package com.banco.emprestimo.dto;

import com.banco.emprestimo.model.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}