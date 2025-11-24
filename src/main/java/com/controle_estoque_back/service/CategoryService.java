package com.controle_estoque_back.service;

import com.controle_estoque_back.dto.CategoryRequestDTO;
import com.controle_estoque_back.dto.CategoryResponseDTO;
import com.controle_estoque_back.entity.Category;
import com.controle_estoque_back.mapper.CategoryMapper;
import com.controle_estoque_back.repository.CategoryRepository;
import com.controle_estoque_back.exceptions.StatusEntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.NoSuchElementException;

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
        return mapper.toResponseDTO(categoryRepository.findById(id).orElseThrow(() -> 
                        new StatusEntityNotFoundException("Categoria não encontrada: id=" + id)));
    }

    public CategoryResponseDTO save(CategoryRequestDTO dto) {

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório");
        }

        Category category = mapper.toEntity(dto);

        try {
            return mapper.toResponseDTO(categoryRepository.save(category));
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Não foi possível salvar a categoria", ex);
        }
    }

    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {

        Category existing = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new StatusEntityNotFoundException("Categoria não encontrada para atualização: id=" + id));

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Nome da categoria não pode ser vazio");
        }

        mapper.updateEntity(existing, dto);

        try {
            return mapper.toResponseDTO(categoryRepository.save(existing));
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("Erro de integridade ao atualizar categoria", ex);
        }
    }

    public void delete(Long id) {

        if (!categoryRepository.existsById(id)) {
            throw new NoSuchElementException("Categoria não encontrada para exclusão: id=" + id);
        }

        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Não é possível excluir a categoria pois está em uso",
                    ex
            );
        }
    }
}