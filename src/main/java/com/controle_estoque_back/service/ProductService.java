package com.controle_estoque_back.service;

import com.controle_estoque_back.dto.ProductRequestDTO;
import com.controle_estoque_back.dto.ProductResponseDTO;
import com.controle_estoque_back.entity.Product;
import com.controle_estoque_back.entity.Category;
import com.controle_estoque_back.mapper.ProductMapper;
import com.controle_estoque_back.repository.CategoryRepository;
import com.controle_estoque_back.repository.ProductRepository;
import com.controle_estoque_back.exceptions.StatusEntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ProductMapper mapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new StatusEntityNotFoundException("Produto não encontrado: id=" + id));

        return mapper.toResponseDTO(product);
    }

    public ProductResponseDTO save(ProductRequestDTO dto) {

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new StatusEntityNotFoundException(
                                "Categoria vinculada não existe: id=" + dto.getCategoryId()));

        Product product = mapper.toEntity(dto, category);

        try {
            return mapper.toResponseDTO(productRepository.save(product));
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Erro ao salvar produto — possivelmente nome duplicado ou categoria inválida",
                    ex
            );
        }
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {

        Product existing = productRepository.findById(id)
                .orElseThrow(() ->
                        new StatusEntityNotFoundException("Produto não encontrado para atualização: id=" + id));

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new StatusEntityNotFoundException(
                                "Categoria vinculada não existe: id=" + dto.getCategoryId()));

        mapper.updateEntity(existing, dto);
        existing.setCategory(category);

        try {
            return mapper.toResponseDTO(productRepository.save(existing));
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Erro de integridade ao atualizar o produto",
                    ex
            );
        }
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Produto não encontrado para exclusão: id=" + id);
        }

        try {
            productRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Não é possível excluir o produto pois está em uso",
                    ex
            );
        }
    }

    @Transactional
    public void adjustPrices(Double percentage) {

        if (percentage == null) {
            throw new IllegalArgumentException("Percentual não pode ser nulo");
        }

        productRepository.findAll().forEach(p -> {
            double updated = p.getUnitPrice() + (p.getUnitPrice() * (percentage / 100));
            p.setUnitPrice(updated);
        });
    }
}

