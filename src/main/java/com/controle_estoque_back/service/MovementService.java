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

        // 404 – Produto não encontrado
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() ->
                        new StatusEntityNotFoundException("Produto não encontrado: id=" + dto.productId()));

        // 400 – Quantidade inválida
        if (dto.quantityMoved() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que 0");
        }

        // 400 – Estoque insuficiente para saída
        if (dto.movementType() == MovementType.EXIT
                && dto.quantityMoved() > product.getQuantityInStock()) {
            throw new IllegalArgumentException("Estoque insuficiente para realizar a saída");
        }

        // Atualizar estoque
        if (dto.movementType() == MovementType.ENTRY) {
            product.setQuantityInStock(product.getQuantityInStock() + dto.quantityMoved());
        } else { // MovementType.EXIT
            product.setQuantityInStock(product.getQuantityInStock() - dto.quantityMoved());
        }

        // Salvar produto com estoque atualizado
        try {
            productRepository.save(product);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Erro ao atualizar o estoque do produto", ex
            );
        }

        // Criar registro de movimento
        Movement movement = mapper.toEntity(dto);

        try {
            return mapper.toResponseDTO(movementRepository.save(movement));
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Erro ao registrar o movimento no banco de dados", ex
            );
        }
    }
}

