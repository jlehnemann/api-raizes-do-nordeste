package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.LoyaltyRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.LoyaltyResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.LoyaltyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fidelidade")
@RequiredArgsConstructor
public class LoyaltyController {

    private final LoyaltyService loyaltyService;

    @GetMapping("/buscar/{customerId}")
    @PreAuthorize("hasAnyRole('COUNTER_ATTENDANT', 'UNIT_MANAGER', 'ADMIN')")
    public LoyaltyResponseDTO findByCustomerId(@PathVariable Long customerId) {
        return loyaltyService.findByCustomerId(customerId);
    }

    @GetMapping("/meus-pontos")
    @PreAuthorize("hasRole('CUSTOMER')")
    public LoyaltyResponseDTO findMyLoyaltyPoints() {
        return loyaltyService.findMyLoyaltyPoints();
    }


    @PatchMapping("/meus-pontos/resgate")
    @PreAuthorize("hasRole('CUSTOMER')")
    public LoyaltyResponseDTO redeemLoyaltyPoints(@Valid @RequestBody LoyaltyRequestDTO dto) {
        return loyaltyService.redeemLoyaltyPoints(dto);
    }
}
