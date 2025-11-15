package com.banco.emprestimo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class EmprestimoDTO {

    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @NotNull(message = "Valor solicitado é obrigatório")
    @Positive(message = "O valor deve ser positivo")
    private Double valorSolicitado;

    @NotNull(message = "Quantidade de parcelas é obrigatória")
    @Positive(message = "Quantidade de parcelas deve ser positiva")
    @Max(value = 12, message = "Máximo de 12 parcelas")
    private Integer quantidadeParcelas;
}