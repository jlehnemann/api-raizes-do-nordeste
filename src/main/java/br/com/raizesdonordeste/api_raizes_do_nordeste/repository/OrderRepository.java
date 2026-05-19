package br.com.raizesdonordeste.api_raizes_do_nordeste.repository;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
