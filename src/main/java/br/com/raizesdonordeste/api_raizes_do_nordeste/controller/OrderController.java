package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.OrderRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.OrderResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public OrderResponseDTO createOrder(@Valid @RequestBody OrderRequestDTO dto) {
        System.out.println("orderOrigin: " + dto.orderOrigin());
        System.out.println("unitId: " + dto.unitId());
        System.out.println("orderItemList: " + dto.orderItemList());
        return orderService.createOrder(dto);
    }



}
