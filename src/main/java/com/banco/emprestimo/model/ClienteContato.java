package com.banco.emprestimo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
public class ClienteContato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable= false)
    private User user;

    private String email;
    private String telefone;
    private String cep;
    private String numero;
    private String tipo; // CASA, APTO

    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;

    public ClienteContato() {
    }
}
