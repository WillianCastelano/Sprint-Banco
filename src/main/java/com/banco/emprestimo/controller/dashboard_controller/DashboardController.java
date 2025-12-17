package com.banco.emprestimo.controller.dashboard_controller;

import com.banco.emprestimo.dto.dashboard_dto.DashboardDTO;

import com.banco.emprestimo.service.service_dashboard.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardDTO getDashboard() {
        return dashboardService.gerarDashboard();
    }


}
