package com.controle_estoque_back.controller;

import com.controle_estoque_back.dto.report.*;
import com.controle_estoque_back.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService service;

    @GetMapping("/price-list")
    public List<PriceListDTO> priceList() {
        return service.getPriceList();
    }

    @GetMapping("/balance")
    public List<BalanceDTO> balance() {
        return service.getBalance();
    }

    @GetMapping("/below-min-stock")
    public List<BelowMinStockDTO> belowMinStock() {
        return service.getBelowMinStock();
    }

    @GetMapping("/count-by-category")
    public List<CountByCategoryDTO> countByCategory() {
        return service.getCountByCategory();
    }

    @GetMapping("/most-moved")
    public List<MostMovedDTO> mostMoved() {
        return service.getMostMoved();
    }
}
