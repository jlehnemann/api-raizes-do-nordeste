package br.com.raizesdonordeste.api_raizes_do_nordeste.service;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.StockMovementRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.StockItemResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.StockResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Stock;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.StockItem;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.StockItemRepository;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.StockRepository;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.UnitRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StockService {

    private final StockRepository stockRepository;
    private final StockItemRepository stockItemRepository;
    private final UnitRepository unitRepository;

    public StockResponseDTO findByUnitId(Long unitId) {
        Stock stock = stockRepository.findByUnitId(unitId)
                .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));

        return mapToStockResponseDTO(stock);
    }

    public StockItemResponseDTO stockInItem(Long unitId, Long itemId, StockMovementRequestDTO dto) {
        StockItem stockItem = stockItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item de estoque não encontrado"));

        if (!unitRepository.existsById(unitId)) {
            throw new EntityNotFoundException("Unidade não encontrada");
        }

        if (!stockItem.getStock().getUnit().getId().equals(unitId)) {
            throw new IllegalStateException("Item não pertence à unidade informada");
        }

        stockItem.setQuantity(stockItem.getQuantity() + dto.quantity());

        if (stockItem.getQuantity() > 0) {
            stockItem.getProduct().setActive(true);
        }

        // log para auditoria
        log.info("Entrada de estoque | item={} | quantidade={} | total={}",
                stockItem.getName(), dto.quantity(), stockItem.getQuantity());

        StockItem savedStockItem = stockItemRepository.save(stockItem);
        return mapToStockItemResponseDTO(savedStockItem);
    }

    public StockItemResponseDTO stockOutItem(Long unitId, Long itemId, StockMovementRequestDTO dto) {
        StockItem stockItem = stockItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item de estoque não encontrado"));

        if (!unitRepository.existsById(unitId)) {
            throw new EntityNotFoundException("Unidade não encontrada");
        }

        if (!stockItem.getStock().getUnit().getId().equals(unitId)) {
            throw new IllegalStateException("Item não pertence à unidade informada");
        }

        if (dto.quantity() > stockItem.getQuantity()) {
            throw new IllegalStateException("Estoque insuficiente para o movimento solicitado");
        }

        stockItem.setQuantity(stockItem.getQuantity() - dto.quantity());

        if (stockItem.getQuantity() == 0) {
            stockItem.getProduct().setActive(false);
        }

        // log para auditoria
        log.info("Saída de estoque | item={} | quantidade={} | total={}",
                stockItem.getName(), dto.quantity(), stockItem.getQuantity());

        StockItem savedStockItem = stockItemRepository.save(stockItem);
        return mapToStockItemResponseDTO(savedStockItem);
    }


    private StockResponseDTO mapToStockResponseDTO(Stock stock) {
        return new StockResponseDTO(
                stock.getId(),
                stock.getUnit().getId(),
                stock.getUnit().getName(),
                stock.getStockItemList().stream()
                        .map(item -> new StockItemResponseDTO(
                                item.getId(),
                                item.getName(),
                                item.getQuantity(),
                                item.getProduct().getId(),
                                item.getProduct().getName()
                        ))
                        .toList()
        );
    }

    private StockItemResponseDTO mapToStockItemResponseDTO(StockItem stockItem) {
        return new StockItemResponseDTO(
                stockItem.getId(),
                stockItem.getName(),
                stockItem.getQuantity(),
                stockItem.getProduct().getId(),
                stockItem.getProduct().getName()
        );
    }
}
