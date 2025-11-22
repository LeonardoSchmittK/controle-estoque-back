package com.controle_estoque_back.dto;

import com.controle_estoque_back.entity.MovementType;

public record MovementRequestDTO(
        Long productId,
        Integer quantityMoved,
        MovementType movementType
) {}

