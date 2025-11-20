package com.controle_estoque_back.mapper;

import com.controle_estoque_back.dto.ProductRequestDTO;
import com.controle_estoque_back.dto.ProductResponseDTO;
import com.controle_estoque_back.entity.Product;
import com.controle_estoque_back.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductRequestDTO dto, Category category) {
        Product entity = new Product();
        entity.setName(dto.getName());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setUnit(dto.getUnit());
        entity.setQuantityInStock(dto.getQuantityInStock());
        entity.setMinQuantity(dto.getMinQuantity());
        entity.setMaxQuantity(dto.getMaxQuantity());
        entity.setCategory(category);
        return entity;
    }

    public ProductResponseDTO toResponseDTO(Product entity) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setUnit(entity.getUnit());
        dto.setQuantityInStock(entity.getQuantityInStock());
        dto.setMinQuantity(entity.getMinQuantity());
        dto.setMaxQuantity(entity.getMaxQuantity());
        dto.setCategoryId(entity.getCategory().getId());
        return dto;
    }

    public void updateEntity(Product entity, ProductRequestDTO dto) {
        entity.setName(dto.getName());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setUnit(dto.getUnit());
        entity.setQuantityInStock(dto.getQuantityInStock());
        entity.setMinQuantity(dto.getMinQuantity());
        entity.setMaxQuantity(dto.getMaxQuantity());
    }
}
