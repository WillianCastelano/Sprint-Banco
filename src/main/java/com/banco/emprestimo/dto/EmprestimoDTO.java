package com.banco.emprestimo.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmprestimoDTO {

    @NotBlank(message = "Informe o CPF")
    @Pattern(
            regexp = "\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}",
            message = "CPF deve estar no formato 000.000.000-00"
    )
    private String cpf;

    @NotNull(message = "Informe o valor solicitado")
    @Positive(message = "O valor deve ser positivo")
    private Double valorSolicitado;

    @NotNull(message = "Informe a quantidade de parcelas")
    @Positive(message = "Quantidade de parcelas deve ser positiva")
    @Max(value = 12, message = "Máximo de 12 parcelas")
    private Integer quantidadeParcelas;
}