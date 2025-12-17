package com.banco.emprestimo.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;



@Service
public class PixService {

    private final WebClient webClient;


    public PixService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Long getTotalPix() {
        String token = webClient.post()
                .uri("http://localhost:8081/auth/login")
                .bodyValue(Map.of(
                        "login", "user-api-wallace3",
                        "password", "123123123"
                ))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String tokenClear = (token != null) ? token.replace("{\"token\":\"", "").replace("\"}", "") : "";


        Map<String, Object> response = webClient.get()
                .uri("/api/transferencias")
                .header("Authorization", "Bearer " + tokenClear)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        if (response == null) return 0L;

        Object dataObj = response.get("data");

        if (dataObj instanceof List<?> list) {
            // Filtra apenas os elementos que são Map (cada transferência)
            long count = list.stream()
                    .filter(item -> item instanceof Map)
                    .count();
            return count;
        }

        return 0L;
    }
}
