package br.com.raizesdonordeste.api_raizes_do_nordeste.service;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.OrderItemRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.OrderRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.OrderItemResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.OrderResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.*;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UnitRepository unitRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final DiscountRepository discountRepository;


    public OrderResponseDTO createOrder(OrderRequestDTO dto) {

        Unit unit = unitRepository.findById(dto.unitId()).
                orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada"));
        Customer customer = dto.customerId() != null ? customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado")) : null;

        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderItemRequestDTO itemDTO : dto.orderItemList()) {
            Product product = productRepository.findById(itemDTO.productId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

            OrderItem orderItem = new OrderItem(product, itemDTO.quantity(), product.getUnitPrice());

            Discount discount = discountRepository.findByProduct(product).orElse(null);
            if (discount != null && discount.getValidUntil().isAfter(LocalDateTime.now())) {
                orderItem.setDiscount(discount);
            }

            orderItemList.add(orderItem);
        }

        BigDecimal orderTotal = orderItemList.stream()
                .map(item -> {
                    BigDecimal subtotal = item.getUnitPrice()
                            .multiply(new BigDecimal(item.getQuantity()));
                    if (item.getDiscount() != null) {
                        BigDecimal desconto = subtotal
                                .multiply(item.getDiscount().getDiscountPercentage());
                        subtotal = subtotal.subtract(desconto);
                    }
                    return subtotal;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order(dto.orderOrigin(), unit, customer, orderItemList);
        order.setOrderTotal(orderTotal);

        Order savedOrder = orderRepository.save(order);

        //log para auditoria
        log.info("Pedido criado | id={} | canal={} | unidade={} | cliente={} | total={}",
                savedOrder.getId(),
                savedOrder.getOrderOrigin(),
                savedOrder.getUnit().getName(),
                savedOrder.getCustomer() != null ? savedOrder.getCustomer().getId() : "totem",
                savedOrder.getOrderTotal());


        return mapToResponseDTO(savedOrder);
    }

    public OrderResponseDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
        return mapToResponseDTO(order);
    }



    private OrderResponseDTO mapToResponseDTO(Order order) {

        return new OrderResponseDTO(
                order.getId(),
                order.getIdempotencyKey(),
                order.getOrderOrigin(),
                order.getOrderStatus(),
                order.getOrderTotal(),
                order.getCreatedAt(),
                order.getUnit().getId(),
                order.getUnit().getName(),
                order.getCustomer() != null ? order.getCustomer().getId() : null,
                order.getOrderItemList().stream()
                        .map(orderItem -> new OrderItemResponseDTO(
                                orderItem.getId(),
                                orderItem.getProduct().getName(),
                                orderItem.getQuantity(),
                                orderItem.getUnitPrice(),
                                orderItem.getItemSubtotal(),
                                orderItem.getDiscount() != null ? orderItem.getDiscount().getName() : null,
                                orderItem.getDiscount() != null ? orderItem.getDiscount().getDiscountPercentage() : null
                        ))
                        .toList()
        );
    }

}
