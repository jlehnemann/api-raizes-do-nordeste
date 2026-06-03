package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "unit_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(nullable = false)
    private boolean active;

    @OneToOne(mappedBy = "unit", cascade = CascadeType.ALL)
    private Stock stock;


    public Unit (String name, String city, String state) {
        this.name = name;
        this.city = city;
        this.state = state;
    }

    @PrePersist
    public void prePersist() {
        this.active = true;
    }



}
