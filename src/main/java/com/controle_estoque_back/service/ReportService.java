package com.controle_estoque_back.service;

import com.controle_estoque_back.dto.report.*;
import com.controle_estoque_back.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<PriceListDTO> getPriceList() {
        return reportRepository.getPriceList();
    }

    public List<BalanceDTO> getBalance() {
        return reportRepository.getBalance();
    }

    public List<BelowMinStockDTO> getBelowMinStock() {
        return reportRepository.getBelowMinStock();
    }

    public List<CountByCategoryDTO> getCountByCategory() {
        return reportRepository.getCountByCategory();
    }

    public List<MostMovedDTO> getMostMoved() {
        return reportRepository.getMostMoved();
    }
}

