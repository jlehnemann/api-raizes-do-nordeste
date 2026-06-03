package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.UnitRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PageResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.UnitResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.UnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unidades")
@RequiredArgsConstructor
public class UnitController {

    private final UnitService unitService;

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public UnitResponseDTO createUnitAndStock(@Valid @RequestBody UnitRequestDTO dto) {
        return unitService.createUnitAndStock(dto);
    }

    @GetMapping("/buscar/{id}")
    public UnitResponseDTO findById(@PathVariable Long id) {
        return unitService.findById(id);
    }

    @GetMapping("/buscar-todas")
    public PageResponseDTO<UnitResponseDTO> findAll(@PageableDefault(sort = "id") Pageable pageable) {
        return unitService.findAll(pageable);
    }

    @PatchMapping("/desativar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void deactivate(@PathVariable Long id) {
        unitService.deactivate(id);
    }


}
