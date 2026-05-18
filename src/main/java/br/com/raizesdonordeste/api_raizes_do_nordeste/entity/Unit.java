package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "unit_tb")
@NoArgsConstructor
@Getter
@Setter
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_seq")
    @SequenceGenerator(name = "unit_seq", sequenceName = "unit_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

}
