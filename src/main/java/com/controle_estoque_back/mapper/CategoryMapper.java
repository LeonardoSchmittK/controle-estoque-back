package com.controle_estoque_back.mapper;

import com.controle_estoque_back.dto.CategoryRequestDTO;
import com.controle_estoque_back.dto.CategoryResponseDTO;
import com.controle_estoque_back.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequestDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity.setSize(dto.getSize());
        entity.setPackaging(dto.getPackaging());
        return entity;
    }

    public CategoryResponseDTO toResponseDTO(Category entity) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSize(entity.getSize());
        dto.setPackaging(entity.getPackaging());
        return dto;
    }

    public void updateEntity(Category entity, CategoryRequestDTO dto) {
        entity.setName(dto.getName());
        entity.setSize(dto.getSize());
        entity.setPackaging(dto.getPackaging());
    }
}
