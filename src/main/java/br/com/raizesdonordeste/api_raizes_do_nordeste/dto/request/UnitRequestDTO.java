package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UnitRequestDTO(
        @NotBlank (message = "Nome é obrigatório")
        String name,
        @NotBlank (message = "Cidade é obrigatória")
        String city,
        @NotBlank (message = "Estado é obrigatório")
        @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres")
        String state
) {
}
