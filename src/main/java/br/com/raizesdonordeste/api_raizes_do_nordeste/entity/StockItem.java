package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class StockItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_item_seq")
    @SequenceGenerator(name = "stock_item_seq", sequenceName = "stock_item_seq", allocationSize = 1)
    private Long id;
    private String name;
    private Long quantity;
    private boolean available;
    private Product product;
    private Stock stock;

}
