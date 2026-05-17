package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.PaymentStatus;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_seq", allocationSize = 1)
    private Long id;
    private Order order;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;

}
