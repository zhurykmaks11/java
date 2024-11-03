package com.example.TaxiService.controller;

import ch.qos.logback.core.model.Model;
import com.example.TaxiService.model.Norder;
import com.example.TaxiService.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // Створення звіту
    @PostMapping("/create")
    public Norder createReport(@RequestBody Norder report) {
        return reportService.createReport(report);
    }

    @GetMapping("/reports")
    public String viewReports(Norder norder) {
        List<Norder> reports = reportService.getAllReports();
        norder.addAttribute("reports", reports);
        return "report.html"; // Назва HTML шаблону
    }

    // Отримання всіх звітів
    @GetMapping
    public List<Norder> getAllReports() {
        return reportService.getAllReports();
    }

    // Отримання звіту за ID
    @GetMapping("/{id}")
    public Norder getReportById(@PathVariable Long id) {
        return reportService.getReportById(id);
    }
}
