package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DiscountRequestDTO(
        @NotBlank(message = "Nome é obrigatório")
        String name,
        @NotNull(message = "Data é obrigatória")
        @Future(message = "Data precisa ser futura")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime validUntil,
        @NotNull(message = "Percentual de desconto é obrigatório")
        @Positive(message = "Percentual de desconto deve ser positivo")
        @DecimalMax(value = "1.0", message = "Valor máximo de desconto é 100%")
        BigDecimal discountPercentage,
        @NotNull(message = "Produto é obrigatório")
        Long productId
) {}
