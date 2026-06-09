package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.LoyaltyRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.LoyaltyResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.LoyaltyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fidelidade")
@RequiredArgsConstructor
@Tag(name = "Fidelidade", description = "Programa de fidelidade — acúmulo e resgate de pontos")
public class LoyaltyController {

    private final LoyaltyService loyaltyService;

    @GetMapping("/buscar/{customerId}")
    @PreAuthorize("hasAnyRole('COUNTER_ATTENDANT', 'UNIT_MANAGER', 'ADMIN')")
    @Operation(summary = "Buscar pontos do cliente", description = "Consulta os pontos de fidelidade de um cliente — requer funcionário")
    @ApiResponse(responseCode = "200", description = "Pontos retornados com sucesso")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    public LoyaltyResponseDTO findByCustomerId(@PathVariable Long customerId) {
        return loyaltyService.findByCustomerId(customerId);
    }

    @GetMapping("/meus-pontos")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Consultar meus pontos", description = "Cliente consulta seus próprios pontos de fidelidade")
    @ApiResponse(responseCode = "200", description = "Pontos retornados com sucesso")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    public LoyaltyResponseDTO findMyLoyaltyPoints() {
        return loyaltyService.findMyLoyaltyPoints();
    }


    @PatchMapping("/meus-pontos/resgate")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Resgatar pontos", description = "Cliente resgata pontos de fidelidade para troca por brindes externos")
    @ApiResponse(responseCode = "200", description = "Pontos resgatados com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @ApiResponse(responseCode = "409", description = "Pontos insuficientes")
    public LoyaltyResponseDTO redeemLoyaltyPoints(@Valid @RequestBody LoyaltyRequestDTO dto) {
        return loyaltyService.redeemLoyaltyPoints(dto);
    }
}
