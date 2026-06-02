package br.com.raizesdonordeste.api_raizes_do_nordeste.repository;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Order;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderOrigin;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByUnitIdAndOrderStatus(
            Long unitId,
            OrderStatus orderStatus,
            Pageable pageable
    );

    Page<Order> findAllByUnitId(Long unitId, Pageable pageable);

    Page<Order> findAllByOrderOrigin(OrderOrigin orderOrigin, Pageable pageable);

}
