package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Unit {
    private Long id;
    private String name;
    private String city;
    private String state;

}
