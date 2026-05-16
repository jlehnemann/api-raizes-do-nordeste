package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Customer extends Person {
    private Long id;
    private LoyaltyProgram loyaltyProgram;
    private User user;

}
