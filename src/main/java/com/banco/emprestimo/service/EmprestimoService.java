package com.banco.emprestimo.service;

import com.banco.emprestimo.exception.BusinessException;
import com.banco.emprestimo.exception.NotFoundException;
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

        if (emprestimo.getQuantidadeParcelas() == null || emprestimo.getQuantidadeParcelas() <= 0) {
            throw new BusinessException("A quantidade de parcelas deve ser maior que zero.");
        }

        emprestimo.setCodigoContrato(UUID.randomUUID().toString());
        emprestimo.setStatus(StatusEmprestimo.PENDENTE);

        double valorParcela =
                emprestimo.getValorSolicitado() / emprestimo.getQuantidadeParcelas();

        emprestimo.setValorParcela(valorParcela);
        emprestimo.setDataAprovacao(null);

        return repository.save(emprestimo);
    }



    public Emprestimo consultarPorContrato(String codigoContrato) {
        return repository.findByCodigoContrato(codigoContrato)
                .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado ou número de contrato não existe."));
    }



    public List<Emprestimo> listarPorCpf(String cpf) {
        List<Emprestimo> emprestimos = repository.findByCpf(cpf);

        if (emprestimos.isEmpty()){
            throw new NotFoundException("Nenhum empréstimo encontrado para o CPF; " + cpf);
        }

        return emprestimos;
    }


    public Emprestimo atualizarStatus(String codigoContrato, String novoStatus) {

        Emprestimo emprestimo = repository.findByCodigoContrato(codigoContrato)
                .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado."));



        StatusEmprestimo statusEnum;

        try {
            statusEnum = StatusEmprestimo.valueOf(novoStatus.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BusinessException("Status informado é inválido.");
        }

        emprestimo.setStatus(statusEnum);

        if (statusEnum == StatusEmprestimo.APROVADO) {
            emprestimo.setDataAprovacao(LocalDate.now());
        }

        return repository.save(emprestimo);
    }


    public void deletarPorContrato(String codigoContrato) {

        Emprestimo emprestimo = repository.findByCodigoContrato(codigoContrato)
                .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado."));

        repository.delete(emprestimo);
    }

}
