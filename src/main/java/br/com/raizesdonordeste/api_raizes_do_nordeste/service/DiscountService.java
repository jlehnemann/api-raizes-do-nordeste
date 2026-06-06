package br.com.raizesdonordeste.api_raizes_do_nordeste.service;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.DiscountRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.DiscountResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.PageResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Discount;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Product;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.DiscountRepository;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ProductRepository productRepository;


    public DiscountResponseDTO create(DiscountRequestDTO dto) {
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(()-> new EntityNotFoundException("Produto não encontrado"));

        Discount discount = new Discount(dto.name(), dto.validUntil(), dto.discountPercentage(), product);

        Discount savedDiscount = discountRepository.save(discount);

        log.info("Promoção criada | nome ={} | validade={} | percentual={} | produto={} | funcionário={}",
                savedDiscount.getName(), savedDiscount.getValidUntil(), savedDiscount.getDiscountPercentage(),
                savedDiscount.getProduct().getId(), getCurrentUserEmail());

        return mapToResponseDTO(savedDiscount);
    }

    public PageResponseDTO<DiscountResponseDTO> findAllActiveDiscounts(Pageable pageable) {
        Page<Discount> discounts =
                discountRepository.findAllByActiveTrueAndValidUntilAfter(LocalDateTime.now(), pageable);

        return PageResponseDTO.of(discounts.map(this::mapToResponseDTO));
    }

    public DiscountResponseDTO findById(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Promoção não encontrada"));

        return mapToResponseDTO(discount);
    }

    public void deactivate(Long id) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Promoção não encontrada"));

        log.info("Promoção desativada | nome ={} | validade={} | percentual={} | produto={} | funcionário={}",
                discount.getName(), discount.getValidUntil(), discount.getDiscountPercentage(),
                discount.getProduct().getId(), getCurrentUserEmail());

        discount.setActive(false);

        discountRepository.save(discount);
    }

    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : "desconhecido";
    }

    private DiscountResponseDTO mapToResponseDTO(Discount discount) {
        return new DiscountResponseDTO(
                discount.getId(),
                discount.getName(),
                discount.getValidUntil(),
                discount.getDiscountPercentage(),
                discount.getProduct().getId(),
                discount.getProduct().getName(),
                discount.isActive()
        );
    }

}
