package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StockMovementRequestDTO(
        @NotNull(message = "A quantidade do produto é obrigatória")
        @Positive(message = "A quantidade do produto deve ser positiva")
        Long quantity
) {}
