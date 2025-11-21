package com.banco.emprestimo.mapper;

import com.banco.emprestimo.dto.EmprestimoDTO;
import com.banco.emprestimo.dto.EmprestimoResponseDTO;
import com.banco.emprestimo.model.Emprestimo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmprestimoMapper {

    Emprestimo toEntity(EmprestimoDTO dto);

    @Mapping(source = "status", target = "status")
    EmprestimoResponseDTO toResponseDTO(Emprestimo emprestimo);
}
