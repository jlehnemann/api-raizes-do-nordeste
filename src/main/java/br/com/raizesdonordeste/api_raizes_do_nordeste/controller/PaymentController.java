package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.PaymentRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PaymentResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/processar/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public PaymentResponseDTO processPayment(@PathVariable Long orderId, @Valid @RequestBody PaymentRequestDTO dto) {
        return paymentService.processPayment(orderId, dto);
    }

    @GetMapping("buscar/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER', 'KITCHEN_ATTENDANT', 'COUNTER_ATTENDANT')")
    public PaymentResponseDTO findPaymentByOrderId(@PathVariable Long orderId) {
        return paymentService.findPaymentByOrderId(orderId);
    }
}
