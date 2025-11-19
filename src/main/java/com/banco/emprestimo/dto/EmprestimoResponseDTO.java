package com.banco.emprestimo.dto;

import lombok.Data;

@Data
public class EmprestimoResponseDTO {
    private String cpf;
    private String codigoContrato;
    private Double valorSolicitado;
    private Integer quantidadeParcelas;
    private Double valorParcela;
    private String status;
    private String criadoEm;
}
