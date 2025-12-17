package com.banco.emprestimo.dto.dashboard_dto;


import lombok.Data;

@Data
public class DashboardDTO {
    private Long totalPix;
    private Long totalEmprestimos;
    private Long totalContas;
    private Long totalClientes;
    private Long totalCartoes;
    private Long totalNotificacoes;
}