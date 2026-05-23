package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response;

import java.time.LocalDateTime;

public record CustomerResponseDTO(Long id,
                                  String name,
                                  String email,
                                  String telephone,
                                  String address,
                                  LocalDateTime createdAt,
                                  String accessToken,
                                  String tokenType) {
}
