package com.banco.emprestimo.service;

import com.banco.emprestimo.dto.EmprestimoDTO;
import com.banco.emprestimo.dto.EmprestimoResponseDTO;
import com.banco.emprestimo.exception.BusinessException;
import com.banco.emprestimo.exception.NotFoundException;
import com.banco.emprestimo.model.Emprestimo;
import com.banco.emprestimo.model.StatusEmprestimo;
import com.banco.emprestimo.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {


    private final EmprestimoRepository repository;



    public EmprestimoService(EmprestimoRepository repository) {
        this.repository = repository;

    }

    public EmprestimoResponseDTO criarEmprestimo(EmprestimoDTO dto) {


        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setCpf(dto.getCpf());
        emprestimo.setValorSolicitado(dto.getValorSolicitado());
        emprestimo.setQuantidadeParcelas(dto.getQuantidadeParcelas());

        if (dto.getQuantidadeParcelas() <= 0) {
            throw new BusinessException("A quantidade de parcelas deve ser maior que zero.");
        }

        emprestimo.setCodigoContrato(UUID.randomUUID().toString());
        emprestimo.setStatus(StatusEmprestimo.PENDENTE);

        double valorParcela =
                dto.getValorSolicitado() / dto.getQuantidadeParcelas();

        emprestimo.setValorParcela(valorParcela);

        emprestimo.setDataAprovacao(null);

        Emprestimo salvo = repository.save(emprestimo);

        return toResponseDTO(salvo);
    }



    public EmprestimoResponseDTO consultarPorContrato(String codigoContrato) {
        Emprestimo emprestimo = repository.findByCodigoContrato(codigoContrato)
                .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado."));

        return toResponseDTO(emprestimo);}



    public List<EmprestimoResponseDTO> listarPorCpf(String cpf) {
        List<Emprestimo> lista = repository.findByCpf(cpf);

        if (lista.isEmpty()){
            throw new NotFoundException("Nenhum empréstimo encontrado para o CPF; " + cpf);
        }

        return lista.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    public EmprestimoResponseDTO atualizarStatus(String codigoContrato, String novoStatus) {

        Emprestimo emprestimo = repository.findByCodigoContrato(codigoContrato)
                .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado."));

        try {
            StatusEmprestimo statusEnum = StatusEmprestimo.valueOf(novoStatus.toUpperCase());
            emprestimo.setStatus(statusEnum);

            if (statusEnum == StatusEmprestimo.APROVADO) {
                emprestimo.setDataAprovacao(LocalDate.now());
            }

        } catch (IllegalArgumentException ex) {
            throw new BusinessException("Status informado é inválido.");
        }

        Emprestimo salvo = repository.save(emprestimo);

        return toResponseDTO(salvo);
    }


    public void deletarPorContrato(String codigoContrato) {
        Emprestimo emprestimo = repository.findByCodigoContrato(codigoContrato)
                .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado."));

        repository.delete(emprestimo);
    }


    private EmprestimoResponseDTO toResponseDTO(Emprestimo e) {
        EmprestimoResponseDTO dto = new EmprestimoResponseDTO();
        dto.setCpf(e.getCpf());
        dto.setCodigoContrato(e.getCodigoContrato());
        dto.setValorSolicitado(e.getValorSolicitado());
        dto.setQuantidadeParcelas(e.getQuantidadeParcelas());
        dto.setValorParcela(e.getValorParcela());
        dto.setStatus(e.getStatus().name());
        dto.setCriadoEm(
                e.getDataAprovacao() != null ? e.getDataAprovacao().toString() : null
        );

        return dto;
    }

}
