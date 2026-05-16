package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class StockItem {
    private Long id;
    private String name;
    private Long quantity;
    private boolean available;
    private Product product;
    private Stock stock;

}
