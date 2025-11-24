package com.controle_estoque_back.service;

import com.controle_estoque_back.dto.report.*;
import com.controle_estoque_back.repository.ReportRepository;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ReportService {
    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<PriceListDTO> getPriceList() {
        logger.info("Gerando relatório: Lista de Preços");
        List<PriceListDTO> list = reportRepository.getPriceList();
        logger.info("Relatório Lista de Preços gerado: {} itens", list.size());
        return list;
    }

    public List<BalanceDTO> getBalance() {
        logger.info("Gerando relatório: Balanço de Estoque");
        List<BalanceDTO> list = reportRepository.getBalance();
        logger.info("Relatório de Balanço gerado: {} itens", list.size());
        return list;
    }

    public List<BelowMinStockDTO> getBelowMinStock() {
        logger.info("Gerando relatório: Produtos abaixo da quantidade mínima");
        List<BelowMinStockDTO> list = reportRepository.getBelowMinStock();
        logger.info("Relatório de produtos abaixo do mínimo: {} itens", list.size());
        return list;
    }

    public List<CountByCategoryDTO> getCountByCategory() {
        logger.info("Gerando relatório: Quantidade de produtos por categoria");
        List<CountByCategoryDTO> list = reportRepository.getCountByCategory();
        logger.info("Relatório por categoria gerado: {} categorias", list.size());
        return list;
    }

    public List<MostMovedDTO> getMostMoved() {
        logger.info("Gerando relatório: Produtos mais movimentados");
        List<MostMovedDTO> list = reportRepository.getMostMoved();
        logger.info("Relatório de mais movimentados: {} itens", list.size());
        return list;
    }
}

