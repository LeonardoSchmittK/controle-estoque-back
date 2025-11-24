package com.controle_estoque_back.dto;

import com.controle_estoque_back.entity.MovementType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MovementRequestDTO(
        @NotNull(message = "O ID do produto é obrigatório")
        Long productId,

        @NotNull(message = "A quantidade movimentada é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantityMoved,

        @NotNull(message = "O tipo de movimentação (ENTRY ou EXIT) é obrigatório")
        MovementType movementType
) {}

