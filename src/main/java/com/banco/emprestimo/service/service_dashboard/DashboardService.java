package com.banco.emprestimo.service.service_dashboard;

import com.banco.emprestimo.client.*;
import com.banco.emprestimo.dto.dashboard_dto.DashboardDTO;
import com.banco.emprestimo.service.EmprestimoService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class DashboardService {


    private final EmprestimoService emprestimoService;
    private final WebClient webClient;

    public DashboardService(EmprestimoService emprestimoService, WebClient webClient) {
        this.emprestimoService = emprestimoService;
        this.webClient = webClient;
    }

    public DashboardDTO gerarDashboard() {

        DashboardDTO dto = new DashboardDTO();

        // Chamada via WebClient agora

        dto.setTotalEmprestimos(safeCall(emprestimoService::getTotalEmprestimos));

        dto.setTotalPix(safeCall(this::getTotalPix));

        return dto;
    }

    private Long safeCall(Supplier<Long> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            return 0L;
        }
    }


    private Long getTotalPix() {
        Map<String, Object> response = webClient.get()
                .uri("/api/transferencias")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        if (response == null) return 0L;

        Object dataObj = response.get("data");

        if (dataObj instanceof List<?> list) {
            // Garantir que todos os elementos da lista são Map<String, Object>
            long count = list.stream()
                    .filter(item -> item instanceof Map)
                    .count();
            return count;
        }

        return 0L;
    }


}



