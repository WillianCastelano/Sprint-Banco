package com.banco.emprestimo.service;

import com.banco.emprestimo.dto.EmprestimoDTO;
import com.banco.emprestimo.dto.EmprestimoResponseDTO;
import com.banco.emprestimo.exception.BusinessException;
import com.banco.emprestimo.exception.NotFoundException;
import com.banco.emprestimo.mapper.EmprestimoMapper;
import com.banco.emprestimo.model.Emprestimo;
import com.banco.emprestimo.model.StatusEmprestimo;
import com.banco.emprestimo.repositories.EmprestimoRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {


    private final EmprestimoRepository repository;
    private final EmprestimoMapper mapper;



    public EmprestimoService(EmprestimoRepository repository, EmprestimoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;

    }

    public EmprestimoResponseDTO criarEmprestimo(EmprestimoDTO dto) {

        if (dto.getQuantidadeParcelas() <= 0) {
            throw new BusinessException("A quantidade de parcelas deve ser maior que zero.");
        }

        Emprestimo emprestimo = mapper.toEntity(dto);

        emprestimo.setCodigoContrato(UUID.randomUUID().toString());
        emprestimo.setStatus(StatusEmprestimo.PENDENTE);

        emprestimo.setValorParcela(
                dto.getValorSolicitado() / dto.getQuantidadeParcelas()
        );

        emprestimo.setDataAprovacao(null);

        Emprestimo salvo = repository.save(emprestimo);

        return mapper.toResponseDTO(salvo);
    }




    public EmprestimoResponseDTO consultarPorContrato(String codigoContrato) {
        Emprestimo emprestimo = repository.findByCodigoContrato(codigoContrato)
                .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado."));

        return mapper.toResponseDTO(emprestimo);}



    public List<EmprestimoResponseDTO> listarPorCpf(String cpf) {
        List<Emprestimo> lista = repository.findByCpf(cpf);

        if (lista.isEmpty()){
            throw new NotFoundException("Nenhum empréstimo encontrado para o CPF; " + cpf);
        }

        return lista.stream()
                .map(mapper::toResponseDTO)
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

        return mapper.toResponseDTO(salvo);
    }


    public void deletarPorContrato(String codigoContrato) {
        Emprestimo emprestimo = repository.findByCodigoContrato(codigoContrato)
                .orElseThrow(() -> new NotFoundException("Empréstimo não encontrado."));

        repository.delete(emprestimo);
    }



}
