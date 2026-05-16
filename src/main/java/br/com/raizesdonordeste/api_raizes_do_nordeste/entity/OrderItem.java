package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class OrderItem {
    private Long id;
    private Product product;
    private String name;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal itemSubtotal;
    private Order order;
    private Discount discount;

}
