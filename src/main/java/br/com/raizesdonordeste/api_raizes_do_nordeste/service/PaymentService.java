package br.com.raizesdonordeste.api_raizes_do_nordeste.service;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.PaymentRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PaymentResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Order;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Payment;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderStatus;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.PaymentStatus;
import br.com.raizesdonordeste.api_raizes_do_nordeste.exception.PaymentRefusedException;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.OrderRepository;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;


    public PaymentResponseDTO processPayment(Long orderId, PaymentRequestDTO dto) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Pedido não encontrado"));

        if (order.getOrderStatus() != OrderStatus.PAYMENT_PENDING) {
            throw new IllegalStateException("Pedido não está aguardando pagamento");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Não autenticado");
        }
        String currentUserEmail = authentication.getName();

        if (order.getCustomer() != null &&
                !order.getCustomer().getUser().getEmail().equals(currentUserEmail)) {
            throw new AccessDeniedException("Acesso negado");
        }

        //busca se já existe pagamento ou cria novo
        Payment payment = paymentRepository.findByOrder(order)
                .orElse(new Payment(order, dto.paymentType()));

        // simula o processamento mock (50% de chance de dar pagamento recusado)
        PaymentStatus paymentStatus = Math.random() < 0.5
                ? PaymentStatus.APPROVED
                : PaymentStatus.REFUSED;


        payment.setPaymentType(dto.paymentType());
        payment.setPaymentStatus(paymentStatus);

        Payment savedPayment = paymentRepository.save(payment);

        //altera status se pagamento aprovado
        if (paymentStatus == PaymentStatus.APPROVED) {
            order.setOrderStatus(OrderStatus.PREPARING);
            orderRepository.save(order);

            //log para auditoria
            log.info("Pagamento processado | orderId={} | status={} | tipo={}",
                    orderId, paymentStatus, dto.paymentType());
        }

        if (paymentStatus == PaymentStatus.REFUSED) {
            throw new PaymentRefusedException("Pagamento recusado");
        }

        return mapToResponseDTO(savedPayment);
    }

    public PaymentResponseDTO checkPayment(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Pedido não encontrado"));
        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(()-> new EntityNotFoundException("Pagamento não encontrado"));

        return mapToResponseDTO(payment);
    }


    public PaymentResponseDTO mapToResponseDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getPaymentType(),
                payment.getPaymentStatus(),
                payment.getCreatedAt()
        );
    }
}
