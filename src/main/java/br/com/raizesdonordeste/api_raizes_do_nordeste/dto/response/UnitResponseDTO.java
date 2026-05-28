package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response;

public record UnitResponseDTO(
        Long id,
        String name,
        String city,
        String state,
        boolean active
) {
}
