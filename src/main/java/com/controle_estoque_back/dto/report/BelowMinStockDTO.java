package com.controle_estoque_back.dto.report;

public class BelowMinStockDTO {

    private Long productId;
    private String productName;
    private Integer quantityInStock;
    private Integer minQuantity;

    public BelowMinStockDTO(Long productId, String productName, Integer quantityInStock, Integer minQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.quantityInStock = quantityInStock;
        this.minQuantity = minQuantity;
    }

    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public Integer getQuantityInStock() { return quantityInStock; }
    public Integer getMinQuantity() { return minQuantity; }
}
