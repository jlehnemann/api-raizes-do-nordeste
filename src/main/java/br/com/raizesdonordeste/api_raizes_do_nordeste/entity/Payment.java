package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.PaymentStatus;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.PaymentType;

import java.time.LocalDateTime;

public class Payment {
    private Long id;
    private Order order;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;

}
