package com.example.TaxiService.service;

import com.example.TaxiService.model.Norder;
import com.example.TaxiService.repository.NorderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    private final NorderRepository reportRepository;

    @Autowired
    public ReportService(NorderRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Norder createReport(Norder report) {
        return reportRepository.save(report);
    }

    public List<Norder> getAllReports() {
        return reportRepository.findAll();
    }

    public Norder getReportById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }

    public void createReport(Long id, double distance) {
    }

}
