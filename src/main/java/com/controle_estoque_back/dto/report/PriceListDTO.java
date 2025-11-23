package com.controle_estoque_back.dto.report;

public class PriceListDTO {

    private Long productId;
    private String productName;
    private Double unitPrice;
    private String unit;

    public PriceListDTO(Long productId, String productName, Double unitPrice, String unit) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.unit = unit;
    }

    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public Double getUnitPrice() { return unitPrice; }
    public String getUnit() { return unit; }
}
