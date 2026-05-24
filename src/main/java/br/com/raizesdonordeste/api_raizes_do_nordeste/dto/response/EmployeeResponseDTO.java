package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.Role;

import java.time.LocalDateTime;

public record EmployeeResponseDTO(Long id,
                                  String name,
                                  String email,
                                  String telephone,
                                  String address,
                                  String unitName,
                                  Role role,
                                  LocalDateTime createdAt,
                                  String accessToken,
                                  String tokenType) {
}