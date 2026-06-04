package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record LoyaltyRequestDTO(
        @NotNull(message = "Pontos são obrigatórios")
        @Positive(message = "A quantidade de pontos precisa ser positiva")
        Integer points
) {
}
