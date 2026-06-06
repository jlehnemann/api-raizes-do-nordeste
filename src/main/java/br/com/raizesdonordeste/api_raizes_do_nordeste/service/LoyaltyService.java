package br.com.raizesdonordeste.api_raizes_do_nordeste.service;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.LoyaltyRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.LoyaltyResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Customer;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.LoyaltyProgram;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.User;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoyaltyService {

    private final CustomerRepository customerRepository;


    public LoyaltyResponseDTO findByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new EntityNotFoundException("Cliente não encontrado"));

        LoyaltyProgram loyaltyProgram = customer.getLoyaltyProgram();

        return mapToResponseDTO(loyaltyProgram);
    }


    public LoyaltyResponseDTO findMyLoyaltyPoints() {
         Customer customer = getAuthenticatedCustomer();

        return mapToResponseDTO(customer.getLoyaltyProgram());
    }

    public LoyaltyResponseDTO redeemLoyaltyPoints(LoyaltyRequestDTO dto) {
        Customer customer = getAuthenticatedCustomer();

        Integer currentPoints = customer.getLoyaltyProgram().getLoyaltyPoints();
        Integer pointsToRedeem = dto.points();

        if (pointsToRedeem > currentPoints) {
            throw new IllegalStateException("Quantidade de pontos inválida");
        }

        customer.getLoyaltyProgram().setLoyaltyPoints(currentPoints - pointsToRedeem);

        log.info("Pontos resgatados | cliente={} | pontos resgatados={} | saldo restante={}",
                customer.getId(), pointsToRedeem, currentPoints - pointsToRedeem);

        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponseDTO(savedCustomer.getLoyaltyProgram());
    }

    private Customer getAuthenticatedCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Acesso negado");
        }

        User currentUser = (User) authentication.getPrincipal();

        return customerRepository.findByUser(currentUser)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));
    }

    private LoyaltyResponseDTO mapToResponseDTO(LoyaltyProgram loyaltyProgram) {
        return new LoyaltyResponseDTO(
                loyaltyProgram.getId(),
                loyaltyProgram.getLoyaltyPoints()
        );
    }



}
