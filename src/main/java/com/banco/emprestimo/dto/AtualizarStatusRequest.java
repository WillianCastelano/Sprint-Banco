package com.banco.emprestimo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarStatusRequest {
    private String codigoContrato;
    private String status;
}