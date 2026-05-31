package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderOrigin;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID idempotencyKey;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderOrigin orderOrigin;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private BigDecimal orderTotal;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList;

    public Order(OrderOrigin orderOrigin, Unit unit, Customer customer, List<OrderItem> orderItemList) {
        this.orderOrigin = orderOrigin;
        this.unit = unit;
        this.customer = customer;
        this.orderItemList = orderItemList;
        this.orderItemList.forEach(orderItem -> orderItem.setOrder(this));
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.idempotencyKey == null) {
            this.idempotencyKey =  UUID.randomUUID();
        }
        if (this.orderStatus == null) {
            this.orderStatus = OrderStatus.PAYMENT_PENDING;
        }
        if (this.orderTotal == null) {
            this.orderTotal = BigDecimal.ZERO;
        }
    }
}
