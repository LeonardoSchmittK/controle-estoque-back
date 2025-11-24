package com.controle_estoque_back.service;

import com.controle_estoque_back.dto.CategoryRequestDTO;
import com.controle_estoque_back.dto.CategoryResponseDTO;
import com.controle_estoque_back.entity.Category;
import com.controle_estoque_back.mapper.CategoryMapper;
import com.controle_estoque_back.repository.CategoryRepository;
import com.controle_estoque_back.exceptions.StatusEntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository categoryRepository,
                          CategoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public List<CategoryResponseDTO> findAll() {
        logger.info("Buscando todas as categorias...");
        return categoryRepository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public CategoryResponseDTO findById(Long id) {
        logger.info("Buscando categoria por ID: {}", id);
        return mapper.toResponseDTO(categoryRepository.findById(id).orElseThrow(() -> {
                    logger.warn("Categoria não encontrada: id={}", id);
                    return new StatusEntityNotFoundException("Categoria não encontrada: id=" + id);
                }));
    }

    public CategoryResponseDTO save(CategoryRequestDTO dto) {
        logger.info("Salvando nova categoria: {}", dto);

        if (dto.getName() == null || dto.getName().isBlank()) {
            logger.warn("Tentativa de salvar categoria com nome vazio");
            throw new IllegalArgumentException("Nome da categoria é obrigatório");
        }

        Category category = mapper.toEntity(dto);

        try {
            Category saved = categoryRepository.save(category);
            logger.info("Categoria salva com sucesso: id={}", saved.getId());
            return mapper.toResponseDTO(categoryRepository.save(category));

        } catch (DataIntegrityViolationException ex) {
            logger.error("Erro ao salvar categoria", ex);
            throw new DataIntegrityViolationException("Não foi possível salvar a categoria", ex);
        }
    }

    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        logger.info("Atualizando categoria id={}", id);

        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Tentativa de atualizar categoria inexistente: id={}", id);
                    return new StatusEntityNotFoundException("Categoria não encontrada para atualização: id=" + id);
                });

        if (dto.getName() == null || dto.getName().isBlank()) {
            logger.warn("Tentativa de atualizar categoria com nome inválido");
            throw new IllegalArgumentException("Nome da categoria não pode ser vazio");
        }

        logger.debug("Atualizando dados da categoria existente");
        mapper.updateEntity(existing, dto);

        try {
            Category updated = categoryRepository.save(existing);
            logger.info("Categoria atualizada com sucesso: id={}", updated.getId());
            return mapper.toResponseDTO(categoryRepository.save(existing));

        } catch (DataIntegrityViolationException ex) {
            logger.error("Erro de integridade ao atualizar categoria", ex);
            throw new DataIntegrityViolationException("Erro de integridade ao atualizar categoria", ex);
        }
    }

    public void delete(Long id) {
        logger.info("Tentando excluir categoria id={}", id);

        if (!categoryRepository.existsById(id)) {
            logger.warn("Tentativa de exclusão de categoria inexistente: id={}", id);
            throw new NoSuchElementException("Categoria não encontrada para exclusão: id=" + id);
        }

        try {
            categoryRepository.deleteById(id);
            logger.info("Categoria excluída com sucesso: id={}", id);

        } catch (DataIntegrityViolationException ex) {
            logger.error("Erro ao excluir categoria", ex);
            throw new DataIntegrityViolationException("Não é possível excluir a categoria pois está em uso", ex);
        }
    }
}