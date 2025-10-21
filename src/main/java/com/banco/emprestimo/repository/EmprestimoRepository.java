package com.banco.emprestimo.repository;

import com.banco.emprestimo.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    Optional<Emprestimo> findByCodigoContrato(String codigoContrato);
    List<Emprestimo> findByCpf(String cpf);
}
