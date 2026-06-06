package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response;

import java.util.List;

public record StockResponseDTO(
        Long id,
        Long unitId,
        String unitName,
        List<StockItemResponseDTO> stockItemList
) {
}
