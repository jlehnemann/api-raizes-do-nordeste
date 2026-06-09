package br.com.raizesdonordeste.api_raizes_do_nordeste.controller;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.CustomerRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.EmployeeRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.LoginRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.CustomerResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.EmployeeResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.LoginResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints de autenticação e registro de usuários")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registrar/cliente")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registrar cliente - Endpoint público", description = "Cadastra um novo cliente no sistema")
    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    public CustomerResponseDTO registerCustomer(@Valid @RequestBody CustomerRequestDTO dto) {
        return authService.registerCustomer(dto);
    }

    @PostMapping("/registrar/funcionario")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'UNIT_MANAGER')")
    @Operation(summary = "Registrar funcionário", description = "Cadastra um novo funcionário — requer ADMIN ou UNIT_MANAGER")
    @ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "401", description = "Não autenticado")
    @ApiResponse(responseCode = "403", description = "Sem permissão")
    public EmployeeResponseDTO registerEmployee(@Valid @RequestBody EmployeeRequestDTO dto) {
        return authService.registerEmployee(dto);
    }

    @PostMapping("/login")
    @Operation(summary = "Login - Endpoint público", description = "Autentica o usuário e retorna o token JWT")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO dto) {
        return authService.login(dto, authenticationManager);
    }

}
