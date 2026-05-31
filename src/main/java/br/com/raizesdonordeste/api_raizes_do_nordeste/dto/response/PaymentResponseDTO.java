package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.PaymentStatus;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.PaymentType;

import java.time.LocalDateTime;

public record PaymentResponseDTO(
        Long id,
        Long orderId,
        PaymentType paymentType,
        PaymentStatus paymentStatus,
        LocalDateTime createdAt
) {
}
