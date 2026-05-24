package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Employee extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    private Employee(String name, String telephone, String address, boolean lgpdConsent,
                     Unit unit, User user) {
        this.setName(name);
        this.setTelephone(telephone);
        this.setAddress(address);
        this.setLgpdConsent(lgpdConsent);
        this.setUnit(unit);
        this.user = user;
    }

    public static Employee create(String name, String telephone, String address, boolean lgpdConsent,
                                  Unit unit, User user) {
        return new Employee(name, telephone,address, lgpdConsent, unit, user);
    }

    @PrePersist
    public void prePersist() {
        initializePersonFields();
    }

}
