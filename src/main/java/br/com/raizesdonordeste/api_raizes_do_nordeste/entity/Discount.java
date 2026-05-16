package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Discount {
    private Long id;
    private String name;
    private LocalDateTime validUntil;
    private BigDecimal discount;

}
