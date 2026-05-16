package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

public abstract class Person {
    private String name;
    private String address;
    private String telephone;
    private boolean lgpdConsent;
    private LocalDateTime createdAt;

}
