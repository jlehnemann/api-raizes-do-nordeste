package br.com.raizesdonordeste.api_raizes_do_nordeste.repository;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Product;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.StockItem;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockItemRepository extends JpaRepository<StockItem, Long> {
    Optional<StockItem> findByProductAndStock_Unit(Product product, Unit unit);

}
