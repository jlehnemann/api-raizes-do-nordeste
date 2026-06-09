package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.UnitRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PageResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.UnitResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.UnitService;
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
@RequestMapping("/unidades")
@RequiredArgsConstructor
@Tag(name = "Unidades", description = "Gerenciamento das unidades da rede Raízes do Nordeste")
public class UnitController {

    private final UnitService unitService;

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar unidade", description = "Cadastra uma nova unidade e cria automaticamente o estoque associado")
    @ApiResponse(responseCode = "201", description = "Unidade criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    public UnitResponseDTO createUnitAndStock(@Valid @RequestBody UnitRequestDTO dto) {
        return unitService.createUnitAndStock(dto);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar unidade por ID")
    @ApiResponse(responseCode = "200", description = "Unidade encontrada")
    @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
    public UnitResponseDTO findById(@PathVariable Long id) {
        return unitService.findById(id);
    }

    @GetMapping("/buscar-todas")
    @Operation(summary = "Listar unidades ativas", description = "Retorna todas as unidades ativas com paginação")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public PageResponseDTO<UnitResponseDTO> findAllActiveUnits(@PageableDefault(sort = "id") Pageable pageable) {
        return unitService.findAllActiveUnits(pageable);
    }

    @PatchMapping("/desativar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desativar unidade", description = "Desativa uma unidade — requer ADMIN")
    @ApiResponse(responseCode = "204", description = "Unidade desativada com sucesso")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @ApiResponse(responseCode = "404", description = "Unidade não encontrada")
    public void deactivate(@PathVariable Long id) {
        unitService.deactivate(id);
    }

}
