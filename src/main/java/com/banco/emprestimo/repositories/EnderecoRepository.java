package com.banco.emprestimo.repositories;

import com.banco.emprestimo.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}