package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductPriceRequestDTO(
        @NotNull(message = "Preço é obrigatório")
        @DecimalMin(value = "0.10", message = "Preço precisa ser positivo")
        @Digits(integer = 3, fraction = 2, message = "O preço deve ter no máximo 3 dígitos inteiros e 2 casas decimais")
        BigDecimal unitPrice
) {}
