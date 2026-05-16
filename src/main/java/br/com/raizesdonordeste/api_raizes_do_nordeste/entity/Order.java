package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderOrigin;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Order {
    private Long id;
    private OrderOrigin orderOrigin;
    private OrderStatus orderStatus;
    private BigDecimal orderTotal;
    private Customer customer;
    private LocalDateTime createdAt;
    private List<OrderItem> orderItemList;

}
