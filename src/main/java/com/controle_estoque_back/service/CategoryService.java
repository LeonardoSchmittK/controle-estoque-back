package com.controle_estoque_back.service;

import com.controle_estoque_back.dto.CategoryRequestDTO;
import com.controle_estoque_back.dto.CategoryResponseDTO;
import com.controle_estoque_back.entity.Category;
import com.controle_estoque_back.mapper.CategoryMapper;
import com.controle_estoque_back.repository.CategoryRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository categoryRepository,
                          CategoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public CategoryResponseDTO findById(Long id) {
        return mapper.toResponseDTO(categoryRepository.findById(id).orElseThrow());
    }

    public CategoryResponseDTO save(CategoryRequestDTO dto) {
        Category category = mapper.toEntity(dto);
        return mapper.toResponseDTO(categoryRepository.save(category));
    }

    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        Category category = categoryRepository.findById(id).orElseThrow();
        mapper.updateEntity(category, dto);
        return mapper.toResponseDTO(categoryRepository.save(category));
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}