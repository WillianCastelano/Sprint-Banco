package com.banco.emprestimo.controller;

import com.banco.emprestimo.dto.ClienteContatoRequestDTO;
import com.banco.emprestimo.dto.ResponseDTO;
import com.banco.emprestimo.model.ClienteContato;
import com.banco.emprestimo.service.ClienteContatoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contatos")
public class ClienteContatoController {

    private final ClienteContatoService service;

    public ClienteContatoController(ClienteContatoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<ClienteContato>> salvar(@RequestBody @Valid ClienteContatoRequestDTO dto) {
        return ResponseEntity.ok(
                new ResponseDTO<>("Contato cadastrado com sucesso", service.salvar(dto))
        );
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<ResponseDTO<ClienteContato>> buscarPorUsuario(@PathVariable String userId) {
        return ResponseEntity.ok(
                new ResponseDTO<>("Contato encontrado", service.buscarPorUsuario(userId))
        );
    }

    @GetMapping("/cidade")
    public ResponseEntity<ResponseDTO<List<ClienteContato>>> buscarPorCidade(@RequestParam String nomeCidade) {
        List<ClienteContato> contatos = service.buscarPorCidade(nomeCidade);
        return ResponseEntity.ok(new ResponseDTO<>("Clientes encontrados", contatos));
    }


    @DeleteMapping("/usuario/{userId}")
    public ResponseEntity<ResponseDTO<Void>> deletarPorUsuario(@PathVariable String userId) {
        service.deletarPorUsuario(userId);
        return ResponseEntity.ok(
                new ResponseDTO<>("Contato deletado com sucesso", null)
        );
    }


}
