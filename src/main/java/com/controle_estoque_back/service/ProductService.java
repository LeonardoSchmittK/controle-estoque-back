package com.controle_estoque_back.service;

import com.controle_estoque_back.dto.ProductRequestDTO;
import com.controle_estoque_back.dto.ProductResponseDTO;
import com.controle_estoque_back.entity.Product;
import com.controle_estoque_back.entity.Category;
import com.controle_estoque_back.mapper.ProductMapper;
import com.controle_estoque_back.repository.CategoryRepository;
import com.controle_estoque_back.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return mapper.toResponseDTO(productRepository.findById(id).orElseThrow());
    }

    public ProductResponseDTO save(ProductRequestDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
        Product product = mapper.toEntity(dto, category);
        return mapper.toResponseDTO(productRepository.save(product));
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id).orElseThrow();
        mapper.updateEntity(product, dto);
        return mapper.toResponseDTO(productRepository.save(product));
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public void adjustPrices(Double percentage) {
        productRepository.findAll().forEach(p -> {
            double updated = p.getUnitPrice() + (p.getUnitPrice() * (percentage / 100));
            p.setUnitPrice(updated);
        });
    }
}

