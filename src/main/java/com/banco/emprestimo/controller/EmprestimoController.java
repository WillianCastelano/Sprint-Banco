package com.banco.emprestimo.controller;


import com.banco.emprestimo.dto.AtualizarStatusRequest;
import com.banco.emprestimo.dto.EmprestimoDTO;
import com.banco.emprestimo.model.Emprestimo;
import com.banco.emprestimo.service.EmprestimoService;
import jakarta.validation.Valid;
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
    public Emprestimo criar(@RequestBody @Valid EmprestimoDTO emprestimoDTO) {

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setCpf(emprestimoDTO.getCpf());
        emprestimo.setValorSolicitado(emprestimoDTO.getValorSolicitado());
        emprestimo.setQuantidadeParcelas(emprestimoDTO.getQuantidadeParcelas());


        return service.criarEmprestimo(emprestimo);
    }




    @GetMapping("/{codigoContrato}")
    public Emprestimo consultar(@PathVariable String codigoContrato) {
        return service.consultarPorContrato(codigoContrato);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletarEmprestimo(id);
    }

    @GetMapping("/cpf/{cpf}")
    public List<Emprestimo> listarPorCpf(@PathVariable String cpf) {
        return service.listarPorCpf(cpf);
    }

    @PutMapping("/status")
    public Emprestimo atualizarStatus(@RequestBody AtualizarStatusRequest request) {
        return service.atualizarStatus(
                request.getCodigoContrato(),
                request.getStatus()
        );
    }

}
