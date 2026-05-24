package br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.Role;
import jakarta.validation.constraints.*;

public record EmployeeRequestDTO(@NotBlank(message = "Nome é obrigatório")
                                 String name,
                                 @NotBlank(message = "E-mail é obrigatório")
                                 @Email(message = "E-mail inválido")
                                 String email,
                                 @NotBlank(message = "Senha é obrigatória")
                                 String password,
                                 @NotBlank(message = "Telefone é obrigatório")
                                 @Pattern(regexp = "^[0-9\\s()+\\-]{8,20}$",
                                         message = "Telefone inválido")
                                 String telephone,
                                 @NotBlank(message = "Endereço é obrigatório")
                                 String address,
                                 @NotNull(message = "Unidade é obrigatória")
                                 Long unitId,
                                 @NotNull(message = "Cargo é obrigatório")
                                 Role role,
                                 @AssertTrue(message = "É preciso aceitar os termos da LGPD para cadastrar")
                                 boolean lgpdConsent){
}
