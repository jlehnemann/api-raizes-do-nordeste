package br.com.raizesdonordeste.api_raizes_do_nordeste.repository;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
}
