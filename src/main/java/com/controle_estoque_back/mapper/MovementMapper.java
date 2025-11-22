package com.controle_estoque_back.mapper;

import com.controle_estoque_back.dto.MovementResponseDTO;
import com.controle_estoque_back.entity.Movement;
import org.springframework.stereotype.Component;

@Component
public class MovementMapper {

    public MovementResponseDTO toResponseDTO(Movement movement) {
        return new MovementResponseDTO(
                movement.getId(),
                movement.getProductId(),
                movement.getQuantityMoved(),
                movement.getMovementType().name(),
                movement.getMovementDate()
        );
    }
}

