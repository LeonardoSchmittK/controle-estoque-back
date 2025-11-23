package com.controle_estoque_back.dto.report;

public class MostMovedDTO {

    private Long productId;
    private String productName;
    private Long movementCount;

    public MostMovedDTO(Long productId, String productName, Long movementCount) {
        this.productId = productId;
        this.productName = productName;
        this.movementCount = movementCount;
    }

    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public Long getMovementCount() { return movementCount; }
}
