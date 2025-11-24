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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

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
        logger.info("Buscando todos os produtos...");
        return productRepository.findAll().stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public ProductResponseDTO findById(Long id) {
        logger.info("Buscando produto por ID: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Produto não encontrado: id={}", id);
                    return new StatusEntityNotFoundException("Produto não encontrado: id=" + id);
                });
                        
                        

        logger.debug("Produto encontrado: {}", product.getName());

        return mapper.toResponseDTO(product);
    }

    public ProductResponseDTO save(ProductRequestDTO dto) {
        logger.info("Salvando novo produto: {}", dto);

        if (dto.getName() == null || dto.getName().isBlank()) {
            logger.warn("Tentativa de salvar produto com nome inválido");
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> {
                    logger.warn("Categoria inválida ou não encontrada: id={}", dto.getCategoryId());
                    return new StatusEntityNotFoundException("Categoria vinculada não existe: id=" + dto.getCategoryId());
                });

        Product product = mapper.toEntity(dto, category);

        try {
            Product saved = productRepository.save(product);
            logger.info("Produto salvo com sucesso: id={}", saved.getId());
            return mapper.toResponseDTO(productRepository.save(product));

        } catch (DataIntegrityViolationException ex) {
            logger.error("Erro de integridade ao salvar produto", ex);
            throw new DataIntegrityViolationException("Erro ao salvar produto — possivelmente nome duplicado ou categoria inválida", ex);
        }
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        logger.info("Atualizando produto id={}", id);

        Product existing = productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Tentativa de atualizar produto inexistente: id={}", id);
                    return new StatusEntityNotFoundException("Produto não encontrado para atualização: id=" + id);
                });

        if (dto.getName() == null || dto.getName().isBlank()) {
            logger.warn("Tentativa de atualizar produto com nome vazio");
            throw new IllegalArgumentException("Nome do produto não pode ser vazio");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> {
                    logger.warn("Categoria vinculada não existe: id={}", dto.getCategoryId());
                    return new StatusEntityNotFoundException("Categoria vinculada não existe: id=" + dto.getCategoryId());
                });

        logger.debug("Atualizando entidade de produto existente");
        mapper.updateEntity(existing, dto);
        existing.setCategory(category);

        try {
            Product updated = productRepository.save(existing);
            logger.info("Produto atualizado com sucesso: id={}", updated.getId());
            return mapper.toResponseDTO(productRepository.save(existing));

        } catch (DataIntegrityViolationException ex) {
            logger.error("Erro de integridade ao atualizar produto", ex);
            throw new DataIntegrityViolationException("Erro de integridade ao atualizar o produto", ex);
        }
    }

    public void delete(Long id) {
        logger.info("Tentando excluir produto id={}", id);

        if (!productRepository.existsById(id)) {
            logger.warn("Tentativa de excluir produto inexistente: id={}", id);
            throw new NoSuchElementException("Produto não encontrado para exclusão: id=" + id);
        }

        try {
            logger.info("Produto excluído com sucesso: id={}", id);
            productRepository.deleteById(id);

        } catch (DataIntegrityViolationException ex) {
            logger.error("Erro de integridade ao excluir produto", ex);
            throw new DataIntegrityViolationException("Não é possível excluir o produto pois está em uso", ex);
        }
    }

    @Transactional
    public void adjustPrices(Double percentage) {
        logger.info("Ajustando preços em {}%", percentage);

        if (percentage == null) {
            logger.warn("Percentual para ajuste de preços é nulo");
            throw new IllegalArgumentException("Percentual não pode ser nulo");
        }

        productRepository.findAll().forEach(p -> {
            double updated = p.getUnitPrice() + (p.getUnitPrice() * (percentage / 100));
            logger.debug("Atualizando preço do produto id={} de {} para {}",p.getId(), p.getUnitPrice(), updated);
            p.setUnitPrice(updated);
        });
    }
}

