package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;


@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class Person {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private boolean lgpdConsent;

    @Column
    private LocalDateTime lgpdConsentDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public void initializePersonFields() {
        this.createdAt = LocalDateTime.now();
        if (this.lgpdConsent && this.lgpdConsentDate == null) {
            this.lgpdConsentDate = LocalDateTime.now();
        }
    }

}
