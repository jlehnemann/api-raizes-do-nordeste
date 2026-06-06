package br.com.raizesdonordeste.api_raizes_do_nordeste.service;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.PaymentRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PaymentResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Order;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Payment;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.OrderStatus;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.PaymentStatus;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.PaymentType;
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


    public PaymentResponseDTO process(Long orderId, PaymentRequestDTO dto) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Pedido não encontrado"));

        if (order.getOrderStatus() != OrderStatus.PAYMENT_PENDING) {
            throw new IllegalStateException("Pedido não está aguardando pagamento");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Não autenticado");
        }

        if (order.getCustomer() != null &&
                !order.getCustomer().getUser().getEmail().equals(getCurrentUserEmail())) {
            throw new AccessDeniedException("Acesso negado");
        }

        //busca se já existe pagamento ou cria novo
        Payment payment = paymentRepository.findByOrder(order)
                .orElse(new Payment(order, dto.paymentType()));

        PaymentStatus paymentStatus = dto.paymentType() == PaymentType.MOCK_APPROVED
                ? PaymentStatus.APPROVED : PaymentStatus.REFUSED;

        payment.setPaymentType(dto.paymentType());
        payment.setPaymentStatus(paymentStatus);

        Payment savedPayment = paymentRepository.save(payment);

        //altera status se pagamento aprovado
        if (paymentStatus == PaymentStatus.APPROVED) {
            order.setOrderStatus(OrderStatus.PREPARING);
            orderRepository.save(order);

            //log para auditoria
            log.info("Pagamento processado | orderId={} | status={} | tipo={} | cliente={}",
                    orderId, paymentStatus, dto.paymentType(), getCurrentUserEmail());
        }

        if (paymentStatus == PaymentStatus.REFUSED) {
            throw new PaymentRefusedException("Pagamento recusado");
        }

        return mapToResponseDTO(savedPayment);
    }

    public PaymentResponseDTO findPaymentByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Pedido não encontrado"));
        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(()-> new EntityNotFoundException("Pagamento não encontrado"));

        return mapToResponseDTO(payment);
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : "desconhecido";
    }

    private PaymentResponseDTO mapToResponseDTO(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getOrder().getId(),
                payment.getPaymentType(),
                payment.getPaymentStatus(),
                payment.getCreatedAt()
        );
    }
}
