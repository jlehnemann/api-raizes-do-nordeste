package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.StockMovementRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.StockItemResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.StockResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.StockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/unidade/{unitId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER', 'KITCHEN_ATTENDANT', 'COUNTER_ATTENDANT')")
    public StockResponseDTO findByUnitId(@PathVariable Long unitId) {
        return stockService.findByUnitId(unitId);
    }

    @PatchMapping("/unidade/{unitId}/entrada/{itemId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER', 'KITCHEN_ATTENDANT')")
    public StockItemResponseDTO stockInItem(@PathVariable Long unitId, @PathVariable Long itemId,
                                            @Valid @RequestBody StockMovementRequestDTO dto) {
        return stockService.stockInItem(unitId, itemId, dto);
    }


    @PatchMapping("/unidade/{unitId}/saida/{itemId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER', 'KITCHEN_ATTENDANT')")
    public StockItemResponseDTO stockOutItem(@PathVariable Long unitId, @PathVariable Long itemId,
                                            @Valid @RequestBody StockMovementRequestDTO dto) {
        return stockService.stockOutItem(unitId, itemId, dto);
    }
}
