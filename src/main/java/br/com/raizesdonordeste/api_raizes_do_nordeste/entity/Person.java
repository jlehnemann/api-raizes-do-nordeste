package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Person {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Telefone inválido")
    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private boolean lgpdConsent;

    @Column
    private LocalDateTime lgpdConsentDate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void registrarConsentimento() {
        if (this.lgpdConsent && this.lgpdConsentDate == null) {
            this.lgpdConsentDate = LocalDateTime.now();
        }
    }

}
