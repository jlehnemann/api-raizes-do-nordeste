package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.OrderRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.OrderResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PageResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderOrigin;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderStatus;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public OrderResponseDTO create(@Valid @RequestBody OrderRequestDTO dto) {
        return orderService.create(dto);
    }

    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER', 'KITCHEN_ATTENDANT', 'COUNTER_ATTENDANT')")
    public OrderResponseDTO findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @GetMapping("/meus-pedidos/buscar/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public OrderResponseDTO findMyOrderById(@PathVariable Long id) {
        return orderService.findMyOrderById(id);
    }

    @GetMapping("/unidade/{unitId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER', 'KITCHEN_ATTENDANT', 'COUNTER_ATTENDANT')")
    public PageResponseDTO<OrderResponseDTO> findByUnitIdAndOrderStatus(
            @PathVariable Long unitId, @RequestParam(required = false) OrderStatus orderStatus,
            @PageableDefault(sort = "id") Pageable pageable) {
        return orderService.findByUnitIdAndOrderStatus(unitId, orderStatus, pageable);
    }

    @GetMapping("/admin/buscar-todos")
    @PreAuthorize("hasRole('ADMIN')")
    public PageResponseDTO<OrderResponseDTO> findAllOrders(
            @RequestParam(required = false) OrderOrigin orderOrigin,
            @PageableDefault(sort = "id") Pageable pageable) {
        return orderService.findAllOrders(orderOrigin, pageable);
    }

    @PatchMapping("/pronto/{id}")
    @PreAuthorize("hasAnyRole('UNIT_MANAGER', 'KITCHEN_ATTENDANT')")
    public OrderResponseDTO readyOrder(@PathVariable Long id) {
        return orderService.readyOrder(id);
    }

    @PatchMapping("/entregar/{id}")
    @PreAuthorize("hasAnyRole('UNIT_MANAGER', 'COUNTER_ATTENDANT')")
    public OrderResponseDTO deliverOrder(@PathVariable Long id) {
        return orderService.deliverOrder(id);
    }

    @PatchMapping("/cancelar/{id}")
    @PreAuthorize("hasAnyRole('UNIT_MANAGER', 'KITCHEN_ATTENDANT')")
    public OrderResponseDTO cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }

}