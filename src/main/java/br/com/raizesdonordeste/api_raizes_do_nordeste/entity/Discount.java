package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "discount_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discount_seq")
    @SequenceGenerator(name = "discount_seq", sequenceName = "discount_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime validUntil;

    @Column(nullable = false)
    private BigDecimal discountPercentage;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private boolean active;

    public Discount(String name, LocalDateTime validUntil, BigDecimal discountPercentage, Product product) {
        this.name = name;
        this.validUntil = validUntil;
        this.discountPercentage = discountPercentage;
        this.product = product;
    }

    @PrePersist
    public void prePersist() {
        this.active = true;
    }

}
