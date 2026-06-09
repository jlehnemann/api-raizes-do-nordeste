package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.OrderRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.OrderResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PageResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderOrigin;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderStatus;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Pedidos", description = "Gerenciamento de pedidos — fluxo crítico do sistema")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Criar pedido", description = "Cria um novo pedido. O campo customerId é opcional — quando informado, o cliente ganha pontos de fidelidade ao receber o pedido")
    @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Unidade, cliente ou produto não encontrado")
    @ApiResponse(responseCode = "409", description = "Estoque insuficiente ou produto indisponível")
    public OrderResponseDTO create(@Valid @RequestBody OrderRequestDTO dto) {
        return orderService.create(dto);
    }

    @GetMapping("/buscar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER', 'KITCHEN_ATTENDANT', 'COUNTER_ATTENDANT')")
    @Operation(summary = "Buscar pedido por ID", description = "Acesso restrito a funcionários")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public OrderResponseDTO findById(@PathVariable Long id) {
        return orderService.findById(id);
    }

    @GetMapping("/meus-pedidos/buscar/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Operation(summary = "Buscar meu pedido", description = "Cliente consulta o próprio pedido")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado")
    @ApiResponse(responseCode = "403", description = "Pedido não pertence ao cliente")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public OrderResponseDTO findMyOrderById(@PathVariable Long id) {
        return orderService.findMyOrderById(id);
    }

    @GetMapping("/unidade/{unitId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER', 'KITCHEN_ATTENDANT', 'COUNTER_ATTENDANT')")
    @Operation(summary = "Listar pedidos por unidade", description = "Filtra por status opcionalmente — ex: ?orderStatus=PREPARING")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public PageResponseDTO<OrderResponseDTO> findByUnitIdAndOrderStatus(
            @PathVariable Long unitId, @RequestParam(required = false) OrderStatus orderStatus,
            @PageableDefault(sort = "id") Pageable pageable) {
        return orderService.findByUnitIdAndOrderStatus(unitId, orderStatus, pageable);
    }

    @GetMapping("/admin/buscar-todos")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar todos os pedidos", description = "Visão gerencial — filtra por canal opcionalmente — ex: ?orderOrigin=TOTEM")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    public PageResponseDTO<OrderResponseDTO> findAllOrders(
            @RequestParam(required = false) OrderOrigin orderOrigin,
            @PageableDefault(sort = "id") Pageable pageable) {
        return orderService.findAllOrders(orderOrigin, pageable);
    }

    @PatchMapping("/pronto/{id}")
    @PreAuthorize("hasAnyRole('UNIT_MANAGER', 'KITCHEN_ATTENDANT')")
    @Operation(summary = "Marcar pedido como pronto", description = "Cozinha marca o pedido como pronto para entrega")
    @ApiResponse(responseCode = "200", description = "Status atualizado para READY")
    @ApiResponse(responseCode = "409", description = "Pedido não está em PREPARING")
    public OrderResponseDTO readyOrder(@PathVariable Long id) {
        return orderService.readyOrder(id);
    }

    @PatchMapping("/entregar/{id}")
    @PreAuthorize("hasAnyRole('UNIT_MANAGER', 'COUNTER_ATTENDANT')")
    @Operation(summary = "Entregar pedido", description = "Marca o pedido como entregue e acumula pontos de fidelidade")
    @ApiResponse(responseCode = "200", description = "Status atualizado para DELIVERED")
    @ApiResponse(responseCode = "409", description = "Pedido não está em READY")
    public OrderResponseDTO deliverOrder(@PathVariable Long id) {
        return orderService.deliverOrder(id);
    }

    @PatchMapping("/cancelar/{id}")
    @PreAuthorize("hasAnyRole('UNIT_MANAGER', 'KITCHEN_ATTENDANT')")
    @Operation(summary = "Cancelar pedido", description = "Cancela o pedido e devolve o estoque automaticamente")
    @ApiResponse(responseCode = "200", description = "Pedido cancelado com sucesso")
    @ApiResponse(responseCode = "409", description = "Pedido não pode ser cancelado neste status")
    public OrderResponseDTO cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }

}