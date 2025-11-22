package com.controle_estoque_back.service;

import com.controle_estoque_back.dto.MovementRequestDTO;
import com.controle_estoque_back.dto.MovementResponseDTO;
import com.controle_estoque_back.entity.*;
import com.controle_estoque_back.mapper.MovementMapper;
import com.controle_estoque_back.repository.MovementRepository;
import com.controle_estoque_back.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementService {

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
        return movementRepository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    public MovementResponseDTO save(MovementRequestDTO dto) {
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        if (dto.quantityMoved() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que 0");
        }

        if (dto.movementType() == MovementType.EXIT && dto.quantityMoved() > product.getQuantityInStock()) {
            throw new IllegalArgumentException("Estoque insuficiente para saída");
        }

        if (dto.movementType() == MovementType.ENTRY) {
            product.setQuantityInStock(product.getQuantityInStock() + dto.quantityMoved());
        } else {
            product.setQuantityInStock(product.getQuantityInStock() - dto.quantityMoved());
        }
        productRepository.save(product);

        Movement movement = new Movement();
        movement.setProductId(dto.productId());
        movement.setQuantityMoved(dto.quantityMoved());
        movement.setMovementType(dto.movementType());

        return mapper.toResponseDTO(movementRepository.save(movement));
    }
}

