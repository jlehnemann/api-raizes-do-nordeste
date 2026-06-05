package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        BigDecimal unitPrice,
        boolean active
) {
}
