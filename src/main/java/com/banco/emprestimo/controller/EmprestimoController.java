package com.banco.emprestimo.controller;


import com.banco.emprestimo.model.Emprestimo;
import com.banco.emprestimo.service.EmprestimoService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {

    private final EmprestimoService service;

    public EmprestimoController(EmprestimoService service) {
        this.service = service;
    }

    @Profile("dev")
    @PostMapping("/teste")
    public Emprestimo criarEmprestimoTeste() {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setCpf("12345678900");
        emprestimo.setValorSolicitado(500.0);
        emprestimo.setQuantidadeParcelas(6);
        emprestimo.setSaldoCliente(1000.0);
        emprestimo.setStatus(null);

        return service.criarEmprestimo(emprestimo, emprestimo.getSaldoCliente());
    }


    @PostMapping
    public Emprestimo criar(@RequestBody Emprestimo emprestimo, @RequestParam double saldoCliente) {
        return service.criarEmprestimo(emprestimo, saldoCliente);
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
}
