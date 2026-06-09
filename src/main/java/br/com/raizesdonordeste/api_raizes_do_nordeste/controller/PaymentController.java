package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.PaymentRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PaymentResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
@Tag(name = "Pagamentos", description = "Processamento de pagamentos mock")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/processar/{orderId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Processar pagamento", description = "Processa o pagamento mock do pedido." +
            " Use MOCK_APPROVED para aprovar ou MOCK_REFUSED para recusar")
    @ApiResponse(responseCode = "200", description = "Pagamento aprovado")
    @ApiResponse(responseCode = "402", description = "Pagamento recusado")
    @ApiResponse(responseCode = "403", description = "Pedido não pertence ao cliente/Sem permissão")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    @ApiResponse(responseCode = "409", description = "Pedido não está aguardando pagamento")
    public PaymentResponseDTO process(@PathVariable Long orderId, @Valid @RequestBody PaymentRequestDTO dto) {
        return paymentService.process(orderId, dto);
    }

    @GetMapping("/buscar/{orderId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER', 'KITCHEN_ATTENDANT', 'COUNTER_ATTENDANT')")
    @Operation(summary = "Buscar pagamento por pedido", description = "Consulta o pagamento associado a um pedido")
    @ApiResponse(responseCode = "200", description = "Pagamento encontrado")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    @ApiResponse(responseCode = "404", description = "Pedido ou pagamento não encontrado")
    public PaymentResponseDTO findPaymentByOrderId(@PathVariable Long orderId) {
        return paymentService.findPaymentByOrderId(orderId);
    }
}
