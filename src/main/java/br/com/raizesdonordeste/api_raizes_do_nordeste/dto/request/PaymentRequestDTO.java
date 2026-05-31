package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.PaymentType;
import jakarta.validation.constraints.NotNull;

public record PaymentRequestDTO(
        @NotNull(message = "Forma de pagamento é obrigatória")
        PaymentType paymentType
) {
}
