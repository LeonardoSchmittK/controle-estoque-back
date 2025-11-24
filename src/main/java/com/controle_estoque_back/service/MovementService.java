package com.controle_estoque_back.service;

import com.controle_estoque_back.dto.MovementRequestDTO;
import com.controle_estoque_back.dto.MovementResponseDTO;
import com.controle_estoque_back.entity.*;
import com.controle_estoque_back.mapper.MovementMapper;
import com.controle_estoque_back.repository.MovementRepository;
import com.controle_estoque_back.repository.ProductRepository;
import com.controle_estoque_back.exceptions.StatusEntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class MovementService {
    private static final Logger logger = LoggerFactory.getLogger(MovementService.class);

    private final MovementRepository movementRepository;
    private final ProductRepository productRepository;
    private final MovementMapper mapper;

    public MovementService(MovementRepository movementRepository,
                           ProductRepository productRepository,
                           MovementMapper mapper) {
        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    public List<MovementResponseDTO> findAll() {
        logger.info("Buscando todos os movimentos...");
        List<MovementResponseDTO> list = movementRepository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        logger.info("Total de movimentos encontrados: {}", list.size());
        return list;
    }

     public MovementResponseDTO save(MovementRequestDTO dto) {
        logger.info("Iniciando registro de movimento: productId={}, type={}, quantity={}", dto.productId(), dto.movementType(), dto.quantityMoved());
        
        // 404 – Produto não encontrado
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> {
                    logger.warn("Produto não encontrado: id={}", dto.productId());
                    return new StatusEntityNotFoundException("Produto não encontrado: id=" + dto.productId());
                });

        // 400 – Quantidade inválida
        if (dto.quantityMoved() <= 0) {
            logger.error("Quantidade inválida: {}", dto.quantityMoved());
            throw new IllegalArgumentException("A quantidade deve ser maior que 0");
        }

        // 400 – Estoque insuficiente para saída
        if (dto.movementType() == MovementType.EXIT
                && dto.quantityMoved() > product.getQuantityInStock()) {
            logger.error("Estoque insuficiente. Disponível={}, Solicitado={}",
                    product.getQuantityInStock(), dto.quantityMoved());
            throw new IllegalArgumentException("Estoque insuficiente para realizar a saída");
        }

        // Atualizar estoque
        if (dto.movementType() == MovementType.ENTRY) {
            logger.info("Realizando entrada de estoque...");
            product.setQuantityInStock(product.getQuantityInStock() + dto.quantityMoved());
        } else { // MovementType.EXIT
            logger.info("Realizando saída de estoque...");
            product.setQuantityInStock(product.getQuantityInStock() - dto.quantityMoved());
        }

        // Salvar produto com estoque atualizado
        try {
            productRepository.save(product);
            logger.info("Estoque atualizado com sucesso. Novo estoque={}", product.getQuantityInStock());
        } catch (DataIntegrityViolationException ex) {
            logger.error("Erro ao atualizar estoque no banco", ex);
            throw new DataIntegrityViolationException("Erro ao atualizar o estoque do produto", ex);
        }

        // Registra movimento
        Movement movement = new Movement();
        movement.setProduct(product);
        movement.setQuantityMoved(dto.quantityMoved());
        movement.setMovementType(dto.movementType());

        try {
            MovementResponseDTO response = mapper.toResponseDTO(movementRepository.save(movement));
            logger.info("Movimento registrado com sucesso. movementId={}", response.id());
            return response;

        } catch (DataIntegrityViolationException ex) {
            logger.error("Erro ao registrar movimento", ex);
            throw new DataIntegrityViolationException("Erro ao registrar o movimento no banco de dados", ex);
        }
    }
}

