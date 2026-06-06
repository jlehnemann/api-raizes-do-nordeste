package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.DiscountRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.DiscountResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PageResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promocoes")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','UNIT_MANAGER')")
    public DiscountResponseDTO create(@Valid @RequestBody DiscountRequestDTO dto) {
        return discountService.create(dto);
    }

    @GetMapping("/buscar-todas-ativas")
    public PageResponseDTO<DiscountResponseDTO> findAllActiveDiscounts(@PageableDefault(sort = "id") Pageable pageable) {
        return discountService.findAllActiveDiscounts(pageable);
    }

    @GetMapping("/buscar/{id}")
    public DiscountResponseDTO findById(@PathVariable Long id) {
        return discountService.findById(id);
    }

    @PatchMapping("/desativar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivate(@PathVariable Long id) {
        discountService.deactivate(id);
    }




}
