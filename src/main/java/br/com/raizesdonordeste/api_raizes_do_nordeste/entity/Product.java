package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class Product {
    private Long id;
    private String name;
    private BigDecimal unitPrice;
    private boolean available;

}
