package com.banco.emprestimo.controller;


import com.banco.emprestimo.dto.AtualizarStatusRequest;
import com.banco.emprestimo.dto.EmprestimoDTO;
import com.banco.emprestimo.dto.EmprestimoResponseDTO;
import com.banco.emprestimo.dto.ResponseDTO;
import com.banco.emprestimo.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService service;

    public EmprestimoController(EmprestimoService service) {
        this.service = service;
    }




    @PostMapping
    public ResponseEntity<ResponseDTO<EmprestimoResponseDTO>> criar(@RequestBody @Valid EmprestimoDTO dto) {
        EmprestimoResponseDTO response = service.criarEmprestimo(dto);

        return  ResponseEntity.ok(
                new ResponseDTO<>("Sucesso", response));

    }




    @GetMapping("/{codigoContrato}")
    public ResponseEntity<ResponseDTO<EmprestimoResponseDTO>> consultar(@PathVariable String codigoContrato) {
        EmprestimoResponseDTO response = service.consultarPorContrato(codigoContrato);

        return ResponseEntity.ok(
                new ResponseDTO<>("Sucesso", response));
    }

    @DeleteMapping("/contrato/{codigoContrato}")
    public ResponseEntity<ResponseDTO<Void>> deletar(@PathVariable String codigoContrato) {
        service.deletarPorContrato(codigoContrato);

        return ResponseEntity.ok(
                new ResponseDTO<>("Registro deletado com sucesso!", null));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ResponseDTO<List<EmprestimoResponseDTO>>> listarPorCpf(@PathVariable String cpf) {

        List<EmprestimoResponseDTO> lista = service.listarPorCpf(cpf);

        return ResponseEntity.ok(
                new ResponseDTO<>("Sucesso", lista)
        );
    }



    @PutMapping("/status")
    public ResponseEntity<ResponseDTO<EmprestimoResponseDTO>> atualizarStatus(@RequestBody @Valid AtualizarStatusRequest request) {

        EmprestimoResponseDTO response = service.atualizarStatus(
                request.getCodigoContrato(),
                request.getStatus()
        );

        return ResponseEntity.ok(
                new ResponseDTO<>("Status atualizado com sucesso!", response)
        );
    }

}
