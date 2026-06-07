package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock_item_tb")
@NoArgsConstructor
@Getter
@Setter
public class StockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_item_seq")
    @SequenceGenerator(name = "stock_item_seq", sequenceName = "stock_item_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false) //sem NotNull, pois pode vir nulo do DTO
    private Long quantity = 0L; //salvaguarda caso venha nulo do DTO

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    public StockItem(String name, Long quantity, Product product, Stock stock) {
        this.name = name;
        this.quantity = quantity;
        this.product = product;
        this.stock = stock;
    }
}
