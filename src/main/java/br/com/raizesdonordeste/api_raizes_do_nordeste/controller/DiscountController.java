package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.DiscountRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.DiscountResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PageResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.DiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Promoções", description = "Gerenciamento de descontos e promoções")
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN','UNIT_MANAGER')")
    @Operation(summary = "Criar promoção", description = "Cadastra um desconto associado a um produto")
    @ApiResponse(responseCode = "201", description = "Promoção criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public DiscountResponseDTO create(@Valid @RequestBody DiscountRequestDTO dto) {
        return discountService.create(dto);
    }

    @GetMapping("/buscar-todas-ativas")
    @Operation(summary = "Listar promoções ativas", description = "Retorna promoções ativas e dentro da validade")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public PageResponseDTO<DiscountResponseDTO> findAllActiveDiscounts(@PageableDefault(sort = "id") Pageable pageable) {
        return discountService.findAllActiveDiscounts(pageable);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar promoção por ID")
    @ApiResponse(responseCode = "200", description = "Promoção encontrada")
    @ApiResponse(responseCode = "404", description = "Promoção não encontrada")
    public DiscountResponseDTO findById(@PathVariable Long id) {
        return discountService.findById(id);
    }

    @PatchMapping("/desativar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desativar promoção", description = "Desativa uma promoção — requer ADMIN")
    @ApiResponse(responseCode = "204", description = "Promoção desativada com sucesso")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @ApiResponse(responseCode = "404", description = "Promoção não encontrada")
    public void deactivate(@PathVariable Long id) {
        discountService.deactivate(id);
    }

}
