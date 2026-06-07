package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<StockItem> stockItems;

    public Product(String name, BigDecimal unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    @PrePersist
    public void prePersist() {
        this.active = false;
    }

}
