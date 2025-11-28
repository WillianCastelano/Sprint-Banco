package com.banco.emprestimo.service;

import com.banco.emprestimo.dto.ClienteContatoRequestDTO;
import com.banco.emprestimo.dto.ViaCepResponseDTO;
import com.banco.emprestimo.exception.BusinessException;
import com.banco.emprestimo.model.ClienteContato;
import com.banco.emprestimo.model.User;
import com.banco.emprestimo.repositories.ClienteContatoRepository;
import com.banco.emprestimo.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteContatoService {

    private final ClienteContatoRepository repository;
    private final ViaCepService viaCepService;
    private final UserRepository userRepository;

    public ClienteContatoService(ClienteContatoRepository repository,
                                 ViaCepService viaCepService,
                                 UserRepository userRepository) {
        this.repository = repository;
        this.viaCepService = viaCepService;
        this.userRepository = userRepository;
    }

    public ClienteContato buscarPorUsuario(String userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException("Contato não encontrado para este usuário."));
    }

    public List<ClienteContato> buscarPorCidade(String cidade) {
        List<ClienteContato> contatos = repository.findByCidade(cidade);
        if (contatos.isEmpty()) {
            throw new BusinessException("Nenhum cliente encontrado na cidade: " + cidade);
        }
        return contatos;
    }


    @Transactional
    public ClienteContato salvar(ClienteContatoRequestDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));


        if (repository.existsByUserId(dto.getUserId())) {
            throw new BusinessException("Este cliente já possui contato registrado.");
        }
        ViaCepResponseDTO viaCep = viaCepService.consultarCep(dto.getCep());

        if(viaCep.getErro() != null && viaCep.getErro()) {
            throw new BusinessException("CEP inválido.");
        }




        ClienteContato contato = new ClienteContato();
        contato.setUser(user);
        contato.setEmail(dto.getEmail());
        contato.setTelefone(dto.getTelefone());
        contato.setCep(dto.getCep());
        contato.setNumero(dto.getNumero());
        contato.setTipo(dto.getTipo());

        contato.setLogradouro(viaCep.getLogradouro());
        contato.setBairro(viaCep.getBairro());
        contato.setCidade(viaCep.getLocalidade());
        contato.setUf(viaCep.getUf());

        return repository.save(contato);
    }

    public void deletarPorUsuario(String userId) {
        ClienteContato contato = repository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException("Contato não encontrado para este usuário."));
        repository.delete(contato);
    }
}
