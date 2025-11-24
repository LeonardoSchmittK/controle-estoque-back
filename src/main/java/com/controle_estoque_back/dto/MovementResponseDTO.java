package com.controle_estoque_back.dto;

import java.time.Instant;

public record MovementResponseDTO(
        Long id,
        Long productId,
        Integer quantityMoved,
        String movementType,
        Instant movementDate
) {}
