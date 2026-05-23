package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Customer extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq", allocationSize = 1)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loyalty_program_id")
    private LoyaltyProgram loyaltyProgram;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;


    private Customer(String name, String telephone,
                     String address, boolean lgpdConsent, User user) {
        this.setName(name);
        this.setTelephone(telephone);
        this.setAddress(address);
        this.setLgpdConsent(lgpdConsent);
        this.user = user;
    }

    public static Customer create(String name, String telephone,
                                  String address, boolean lgpdConsent, User user) {
        return new Customer(name, telephone, address, lgpdConsent, user);
    }

    @PrePersist
    public void prePersist() {
        initializePersonFields();
        if (this.loyaltyProgram == null) {
            this.loyaltyProgram = new LoyaltyProgram();
        }
    }

}
