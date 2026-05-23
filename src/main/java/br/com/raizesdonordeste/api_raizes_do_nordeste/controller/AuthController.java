package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.CustomerRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.LoginRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.CustomerResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.LoginResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDTO registerCustomer(@Valid @RequestBody CustomerRequestDTO dto) {
        return authService.registerCustomer(dto);
    }


    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO dto) {
        return authService.login(dto, authenticationManager);
    }
}
