package com.banco.emprestimo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String cpf;

    @NotNull
    @Positive
    private Double valorSolicitado;

    @NotNull
    @Positive
    @Max(12)
    private Integer quantidadeParcelas;

    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status;



    private String codigoContrato;
    private LocalDate dataAprovacao;

    @NotNull
    private Double saldoCliente;


    private Double valorParcela;

}
