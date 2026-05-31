package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemRequestDTO(
        @NotNull(message = "Código do produto é obrigatório")
        Long productId,
        @NotNull(message = "Quantidade é obrigatória")
        @Positive(message = "A quantidade de itens precisa ser positiva")
        Integer quantity
) {
}
