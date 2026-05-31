package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderOrigin;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseDTO(
        Long id,
        UUID idempotencyKey,
        OrderOrigin orderOrigin,
        OrderStatus orderStatus,
        BigDecimal orderTotal,
        LocalDateTime createdAt,
        Long unitId,
        String unitName,
        Long customerId,  //null se totem
        List<OrderItemResponseDTO> orderItemList
) {}
