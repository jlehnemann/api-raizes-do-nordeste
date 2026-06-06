package br.com.raizesdonordeste.api_raizes_do_nordeste.repository;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Discount;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByProduct(Product product);
    Page<Discount> findAllByActiveTrueAndValidUntilAfter(LocalDateTime now, Pageable pageable);


}
