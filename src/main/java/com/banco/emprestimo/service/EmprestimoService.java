package com.banco.emprestimo.service;

import com.banco.emprestimo.model.Emprestimo;
import com.banco.emprestimo.model.StatusEmprestimo;
import com.banco.emprestimo.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class EmprestimoService {


    private final EmprestimoRepository repository;



    public EmprestimoService(EmprestimoRepository repository) {
        this.repository = repository;

    }

    public Emprestimo criarEmprestimo(Emprestimo emprestimo) {
        emprestimo.setCodigoContrato(UUID.randomUUID().toString());


        if (emprestimo.getQuantidadeParcelas() <= 12) {
            emprestimo.setStatus(StatusEmprestimo.APROVADO);
            emprestimo.setDataAprovacao(LocalDate.now());

            double valorParcela = emprestimo.getValorSolicitado() / emprestimo.getQuantidadeParcelas();
            emprestimo.setValorParcela(valorParcela);


        } else {
            emprestimo.setStatus(StatusEmprestimo.REJEITADO);

        }

        return repository.save(emprestimo);
    }


    public Emprestimo consultarPorContrato(String codigoContrato) {
        return repository.findByCodigoContrato(codigoContrato)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado."));
    }

    public void deletarEmprestimo(Long id) {
        repository.deleteById(id);
    }

    public List<Emprestimo> listarPorCpf(String cpf) {
        List<Emprestimo> emprestimos = repository.findByCpf(cpf);

        if (emprestimos.isEmpty()){
            throw new RuntimeException("Nenhum empréstimo encontrado para o CPF; " + cpf);
        }

        return emprestimos;
    }
}
