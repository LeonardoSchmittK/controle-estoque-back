package com.controle_estoque_back.service;

import com.controle_estoque_back.dto.CategoryRequestDTO;
import com.controle_estoque_back.dto.CategoryResponseDTO;
import com.controle_estoque_back.entity.Category;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryService {

    private final Map<Long, Category> database = new HashMap<>();
    private long counter = 1;

    // --------------------------
    //  Métodos de conversão
    // --------------------------

    private CategoryResponseDTO toResponseDTO(Category c) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(c.getId());
        dto.setName(c.getName());
        dto.setSize(c.getSize());
        dto.setPackaging(c.getPackaging());
        return dto;
    }

    private Category toEntity(CategoryRequestDTO dto) {
        Category c = new Category();
        c.setName(dto.getName());
        c.setSize(dto.getSize());
        c.setPackaging(dto.getPackaging());
        return c;
    }

    // --------------------------
    //      MÉTODOS CRUD
    // --------------------------

    public List<CategoryResponseDTO> findAll() {
        return database.values()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public CategoryResponseDTO findById(Long id) {
        Category category = database.get(id);
        return category != null ? toResponseDTO(category) : null;
    }

    public CategoryResponseDTO save(CategoryRequestDTO dto) {
        Category category = toEntity(dto);
        category.setId(counter++);
        database.put(category.getId(), category);
        return toResponseDTO(category);
    }

    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        Category existing = database.get(id);
        if (existing == null) return null;

        existing.setName(dto.getName());
        existing.setSize(dto.getSize());
        existing.setPackaging(dto.getPackaging());

        return toResponseDTO(existing);
    }

    public void delete(Long id) {
        database.remove(id);
    }
}
