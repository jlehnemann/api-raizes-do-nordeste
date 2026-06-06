package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.ProductPriceRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.ProductRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PageResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.ProductResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.ProductService;
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
public class ProductController {

    private final ProductService productService;

    @PostMapping("/criar")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO createProductAndStockItem(@Valid @RequestBody ProductRequestDTO dto) {
        return productService.createProductAndStockItem(dto);
    }

    @GetMapping("/buscar-todos-ativos")
    public PageResponseDTO<ProductResponseDTO> findAllActiveProducts(@PageableDefault(sort = "id") Pageable pageable) {
        return productService.findAllActiveProducts(pageable);
    }

    @GetMapping("/buscar/{id}")
    public ProductResponseDTO findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PatchMapping("/atualizar-preco/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER')")
    public ProductResponseDTO update(@PathVariable Long id, @Valid @RequestBody ProductPriceRequestDTO dto) {
        return productService.updatePrice(id, dto);
    }

    @PatchMapping("/desativar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable Long id) {
        productService.deactivate(id);
    }

}
