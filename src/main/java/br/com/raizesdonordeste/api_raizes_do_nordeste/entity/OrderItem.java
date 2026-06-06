package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_seq")
    @SequenceGenerator(name = "order_item_seq", sequenceName = "order_item_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal itemSubtotal = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    public OrderItem(Product product, Integer quantity, BigDecimal unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    @PrePersist
    public void subtotalCalculation() {
        if (this.unitPrice != null && this.quantity != null) {
            BigDecimal calculatedSubtotal = this.unitPrice.multiply(new BigDecimal(this.quantity));
            if (this.discount != null) {
                BigDecimal calculatedDiscount = calculatedSubtotal.multiply((this.discount.getDiscountPercentage()));
                calculatedSubtotal = calculatedSubtotal.subtract((calculatedDiscount));
            }
            this.itemSubtotal = calculatedSubtotal;
        }
    }

}
