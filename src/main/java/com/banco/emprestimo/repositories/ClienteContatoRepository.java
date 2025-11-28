package com.banco.emprestimo.repositories;

import com.banco.emprestimo.model.ClienteContato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteContatoRepository extends JpaRepository<ClienteContato, Long> {

    boolean existsByUserId(String userId);

    Optional<ClienteContato> findByUserId(String userId);
    @Query("SELECT c FROM ClienteContato c WHERE LOWER(c.cidade) LIKE LOWER(CONCAT('%', :cidade, '%'))")
    List<ClienteContato> findByCidade(@Param("cidade") String cidade);
}
