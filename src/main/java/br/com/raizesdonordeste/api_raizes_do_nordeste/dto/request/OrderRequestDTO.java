package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderOrigin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDTO(
        @NotNull(message = "Canal do pedido é obrigatório")
        OrderOrigin orderOrigin,
        @NotNull(message = "Código da Unidade é obrigatório")
        Long unitId,
        Long customerId,
        @NotEmpty(message = "Itens do pedido são obrigatórios")
        @NotNull(message = "O pedido deve ter pelo menos um item")
        List<OrderItemRequestDTO> orderItemList
) {
}
