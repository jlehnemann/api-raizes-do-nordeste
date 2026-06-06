package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DiscountResponseDTO(
        Long id,
        String name,
        LocalDateTime validUntil,
        BigDecimal discountPercentage,
        Long productId,
        String productName,
        boolean active
) {


}
