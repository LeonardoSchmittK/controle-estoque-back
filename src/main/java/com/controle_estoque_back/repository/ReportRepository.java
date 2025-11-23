package com.controle_estoque_back.repository;

import com.controle_estoque_back.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT new com.controle_estoque_back.dto.report.PriceListDTO(
                p.id, p.name, p.unitPrice, p.unit
            ) FROM Product p
        """)
    List<com.controle_estoque_back.dto.report.PriceListDTO> getPriceList();

    @Query("""
            SELECT new com.controle_estoque_back.dto.report.BalanceDTO(
                p.id, p.name, p.quantityInStock, p.unitPrice,
                (p.unitPrice * p.quantityInStock)
            ) FROM Product p
        """)
    List<com.controle_estoque_back.dto.report.BalanceDTO> getBalance();

    @Query("""
            SELECT new com.controle_estoque_back.dto.report.BelowMinStockDTO(
                p.id, p.name, p.quantityInStock, p.minQuantity
            ) FROM Product p
            WHERE p.quantityInStock < p.minQuantity
        """)
    List<com.controle_estoque_back.dto.report.BelowMinStockDTO> getBelowMinStock();

    @Query("""
            SELECT new com.controle_estoque_back.dto.report.CountByCategoryDTO(
                c.id, c.name, COUNT(p)
            ) FROM Category c LEFT JOIN c.products p
            GROUP BY c.id, c.name
        """)
    List<com.controle_estoque_back.dto.report.CountByCategoryDTO> getCountByCategory();

    @Query("""
            SELECT new com.controle_estoque_back.dto.report.MostMovedDTO(
                m.product.id, m.product.name, COUNT(m)
            ) FROM Movement m
            GROUP BY m.product.id, m.product.name
            ORDER BY COUNT(m) DESC
        """)
    List<com.controle_estoque_back.dto.report.MostMovedDTO> getMostMoved();
}
