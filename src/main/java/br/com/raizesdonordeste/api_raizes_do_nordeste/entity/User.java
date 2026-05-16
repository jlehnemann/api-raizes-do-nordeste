package br.com.raizesdonordeste.api_raizes_do_nordeste.entity;

import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.enums.Role;

import java.time.LocalDateTime;

public class User {
    private Long id;
    private String email;
    private String password;
    private Role role;
    private boolean active;
    private LocalDateTime createdAt;
}
