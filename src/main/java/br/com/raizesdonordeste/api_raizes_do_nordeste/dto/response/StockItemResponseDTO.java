package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response;

public record StockItemResponseDTO(
        Long id,
        String name,
        Long quantity,
        Long productId,
        String productName
) {
}
