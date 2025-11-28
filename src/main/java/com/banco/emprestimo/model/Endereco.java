package com.banco.emprestimo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;

    @Enumerated(EnumType.STRING)
    private TipoResidencia tipo;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}