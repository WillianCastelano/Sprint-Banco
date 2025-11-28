package com.banco.emprestimo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ClienteContatoRequestDTO {

    @NotNull(message = "Usuário é obrigatório")
    private String userId;

    @NotBlank(message = "Email é obrigatório")
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "CEP é obrigatório")
    private String cep;

    @NotBlank(message = "Número é obrigatório")
    private String numero;

    @NotBlank
    @NotNull(message = "Tipo da residência é obrigatório")
    private String tipo;
}

