package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.StockMovementRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.StockItemResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.StockResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estoques")
@RequiredArgsConstructor
@Tag(name = "Estoque", description = "Controle de estoque por unidade")
public class StockController {

    private final StockService stockService;

    @GetMapping("/unidade/{unitId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER', 'KITCHEN_ATTENDANT', 'COUNTER_ATTENDANT')")
    @Operation(summary = "Consultar estoque da unidade",
            description = "Retorna todos os itens de estoque de uma unidade")
    @ApiResponse(responseCode = "200", description = "Estoque retornado com sucesso")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
    public StockResponseDTO findByUnitId(@PathVariable Long unitId) {
        return stockService.findByUnitId(unitId);
    }

    @PatchMapping("/unidade/{unitId}/entrada/{itemId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER', 'KITCHEN_ATTENDANT')")
    @Operation(summary = "Entrada de estoque",
            description = "Aumenta a quantidade de um item — ativa o produto automaticamente se estava inativo")
    @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @ApiResponse(responseCode = "404", description = "Item ou unidade não encontrado")
    public StockItemResponseDTO stockInItem(@PathVariable Long unitId, @PathVariable Long itemId,
                                            @Valid @RequestBody StockMovementRequestDTO dto) {
        return stockService.stockInItem(unitId, itemId, dto);
    }


    @PatchMapping("/unidade/{unitId}/saida/{itemId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER', 'KITCHEN_ATTENDANT')")
    @Operation(summary = "Saída de estoque",
            description = "Diminui a quantidade de um item — desativa o produto automaticamente se zerar")
    @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @ApiResponse(responseCode = "404", description = "Item ou unidade não encontrado")
    @ApiResponse(responseCode = "409", description = "Estoque insuficiente")
    public StockItemResponseDTO stockOutItem(@PathVariable Long unitId, @PathVariable Long itemId,
                                            @Valid @RequestBody StockMovementRequestDTO dto) {
        return stockService.stockOutItem(unitId, itemId, dto);
    }
}
