package br.com.raizesdonordeste.api_raizes_do_nordeste.service;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.ProductPriceRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.ProductRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PageResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.ProductResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Product;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Stock;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.StockItem;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.ProductRepository;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.StockItemRepository;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.StockRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final StockItemRepository stockItemRepository;

    public ProductResponseDTO createProductAndStockItem(ProductRequestDTO dto) {
        Product product = new Product(dto.name(), dto.unitPrice());
        Product savedProduct = productRepository.save(product);

        List<Stock> stocks = stockRepository.findAll();
        for (Stock stock : stocks) {
            StockItem stockItem = new StockItem(savedProduct.getName(), 0L, savedProduct, stock);
            stockItemRepository.save(stockItem);
        }

        log.info("Produto cadastrado | id= {} | nome = {} | preço = {}, ativo = {}",
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getUnitPrice(),
                savedProduct.isActive());

        return mapToResponseDTO(savedProduct);
    }

    public PageResponseDTO<ProductResponseDTO> findAllActiveProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAllByActiveTrue(pageable);

        return PageResponseDTO.of(products.map(this::mapToResponseDTO));
    }

    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Produto não encontrado"));

        return mapToResponseDTO(product);
    }

    public ProductResponseDTO updatePrice(Long id, ProductPriceRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Produto não encontrado"));

        product.setUnitPrice(dto.unitPrice());
        Product savedProduct = productRepository.save(product);

        //log para auditoria
        log.info("Produto atualizado | id={} | nome={} | preço={}",
                savedProduct.getId(), savedProduct.getName(), savedProduct.getUnitPrice());

        return mapToResponseDTO(savedProduct);
    }

    public void deactivate(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Produto não encontrado"));

        product.setActive(false);
        productRepository.save(product);
        log.info("Produto desativado | id={} | nome={}", product.getId(), product.getName());

    }

    private ProductResponseDTO mapToResponseDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getUnitPrice(),
                product.isActive()
        );
    }
}
