package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loyalty_program_tb")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoyaltyProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loyalty_program_seq")
    @SequenceGenerator(name = "loyalty_program_seq", sequenceName = "loyalty_program_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private Integer loyaltyPoints;

    @PrePersist
    public void initializeLoyaltyPoints() {
        if (this.loyaltyPoints == null) {
            this.loyaltyPoints = 0;
        }
    }
}
