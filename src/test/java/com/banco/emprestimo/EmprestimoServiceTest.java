package com.banco.emprestimo;

import com.banco.emprestimo.dto.EmprestimoDTO;
import com.banco.emprestimo.dto.EmprestimoResponseDTO;
import com.banco.emprestimo.exception.BusinessException;
import com.banco.emprestimo.mapper.EmprestimoMapper;
import com.banco.emprestimo.model.Emprestimo;
import com.banco.emprestimo.repositories.EmprestimoRepository;
import com.banco.emprestimo.service.EmprestimoService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class EmprestimoServiceTest {

    @Mock
    private EmprestimoRepository repository;

    @Mock
    private EmprestimoMapper mapper;

    @InjectMocks
    private EmprestimoService service;

   /* @Test
     public void deveLancarExcecaoQuaboParcelaForMenorOuIgualZero(){

        EmprestimoDTO dto = new EmprestimoDTO();
        dto.setQuantidadeParcelas(2);
        dto.setValorSolicitado(1000.0);

        assertThrows(BusinessException.class, () ->{
           service.criarEmprestimo(dto);
        });
    }*/


     @Test
     public  void deveCriarEmprestimoComSucesso() {

        // ---------- Arrange ----------
        EmprestimoDTO dto = new EmprestimoDTO();
        dto.setQuantidadeParcelas(2);
        dto.setValorSolicitado(1000.0);

        Emprestimo emprestimo = new Emprestimo();
        Emprestimo emprestimoSalvo = new Emprestimo();
        EmprestimoResponseDTO responseDTO = new EmprestimoResponseDTO();

        when(mapper.toEntity(dto)).thenReturn(emprestimo);
        when(repository.save(any(Emprestimo.class))).thenReturn(emprestimoSalvo);
        when(mapper.toResponseDTO(emprestimoSalvo)).thenReturn(responseDTO);

        // ---------- Act ----------
        EmprestimoResponseDTO response = service.criarEmprestimo(dto);

        // ---------- Assert ----------
        assertNotNull(response);

        verify(mapper).toEntity(dto);
        verify(repository).save(any(Emprestimo.class));
        verify(mapper).toResponseDTO(emprestimoSalvo);
    }

}
