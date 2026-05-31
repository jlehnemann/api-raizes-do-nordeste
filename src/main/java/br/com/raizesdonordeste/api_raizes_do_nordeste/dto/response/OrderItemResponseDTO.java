package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        Long id,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal itemSubtotal,
        String discountName, //null se não tiver desconto
        BigDecimal discountPercentage //null se não tiver desconto
) {}
