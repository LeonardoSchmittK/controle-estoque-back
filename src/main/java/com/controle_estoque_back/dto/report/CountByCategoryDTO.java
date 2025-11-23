package com.controle_estoque_back.dto.report;

public class CountByCategoryDTO {

    private Long categoryId;
    private String categoryName;
    private Long totalProducts;

    public CountByCategoryDTO(Long categoryId, String categoryName, Long totalProducts) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.totalProducts = totalProducts;
    }

    public Long getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
    public Long getTotalProducts() { return totalProducts; }
}
