package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.ProductPriceRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.ProductRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PageResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.ProductResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.ProductService;
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
@RequestMapping("/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "Gerenciamento de produtos do cardápio")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/criar")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar produto",
            description = "Cadastra um novo produto e cria automaticamente um item de estoque em cada unidade")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @ApiResponse(responseCode = "409", description = "Produto com esse nome já existe")
    public ProductResponseDTO createProductAndStockItem(@Valid @RequestBody ProductRequestDTO dto) {
        return productService.createProductAndStockItem(dto);
    }

    @GetMapping("/buscar-todos-ativos")
    @Operation(summary = "Listar produtos ativos",
            description = "Retorna produtos ativos — produto fica inativo quando estoque zera")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public PageResponseDTO<ProductResponseDTO> findAllActiveProducts(@PageableDefault(sort = "id") Pageable pageable) {
        return productService.findAllActiveProducts(pageable);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar produto por ID")
    @ApiResponse(responseCode = "200", description = "Produto encontrado")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ProductResponseDTO findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PatchMapping("/atualizar-preco/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER')")
    @Operation(summary = "Atualizar preço do produto", description = "Atualiza apenas o preço — o nome do produto não pode ser alterado")
    @ApiResponse(responseCode = "200", description = "Preço atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ProductResponseDTO update(@PathVariable Long id, @Valid @RequestBody ProductPriceRequestDTO dto) {
        return productService.updatePrice(id, dto);
    }

    @PatchMapping("/desativar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Desativar produto", description = "Desativa manualmente um produto por força maior — requer ADMIN")
    @ApiResponse(responseCode = "204", description = "Produto desativado com sucesso")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public void deactivate(@PathVariable Long id) {
        productService.deactivate(id);
    }

}
