package com.banco.emprestimo.service;

import com.banco.emprestimo.dto.ViaCepResponseDTO;
import com.banco.emprestimo.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCepService {

    private static final Logger log = LoggerFactory.getLogger(ViaCepService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    public ViaCepResponseDTO consultarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        try {
            // LOG SEGURO (não expõe dados sensíveis)
            log.info("Consultando ViaCEP para o CEP: {}", cep);

            ViaCepResponseDTO response = restTemplate.getForObject(url, ViaCepResponseDTO.class);

            if (response == null) {
                throw new BusinessException("Resposta vazia da ViaCEP.");
            }

            if (response.getErro() != null && response.getErro()) {
                throw new BusinessException("CEP inválido.");
            }

            return response;

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("Falha ao consultar ViaCEP para o CEP {}", cep);
            throw new BusinessException("Erro ao consultar CEP na ViaCEP.");
        }
    }
}
