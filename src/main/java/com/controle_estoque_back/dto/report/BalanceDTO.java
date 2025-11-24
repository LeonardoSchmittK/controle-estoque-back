package com.controle_estoque_back.dto.report;

public class BalanceDTO {

    private Long productId;
    private String productName;
    private Integer quantityInStock;
    private Double unitPrice;
    private Double totalValue;

    public BalanceDTO(Long productId, String productName, Integer quantityInStock, Double unitPrice, Double totalValue) {
        this.productId = productId;
        this.productName = productName;
        this.quantityInStock = quantityInStock;
        this.unitPrice = unitPrice;
        this.totalValue = totalValue;
    }

    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public Integer getQuantityInStock() { return quantityInStock; }
    public Double getUnitPrice() { return unitPrice; }
    public Double getTotalValue() { return totalValue; }
}
