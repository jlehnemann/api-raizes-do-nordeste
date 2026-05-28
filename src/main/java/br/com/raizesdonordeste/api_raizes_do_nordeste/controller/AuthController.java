package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.CustomerRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.EmployeeRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.LoginRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.CustomerResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.EmployeeResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.LoginResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registrar/cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDTO registerCustomer(@Valid @RequestBody CustomerRequestDTO dto) {
        return authService.registerCustomer(dto);
    }

    @PostMapping("/registrar/funcionario")
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasRole('ADMIN'")
    public EmployeeResponseDTO registerEmployee(@Valid @RequestBody EmployeeRequestDTO dto) {
        return authService.registerEmployee(dto);
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO dto) {
        return authService.login(dto, authenticationManager);
    }

}
