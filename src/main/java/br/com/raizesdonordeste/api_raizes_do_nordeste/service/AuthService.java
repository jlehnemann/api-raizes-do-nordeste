package br.com.raizesdonordeste.api_raizes_do_nordeste.service;

import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.CustomerRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.request.LoginRequestDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.CustomerResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.dto.response.LoginResponseDTO;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.Customer;
import br.com.raizesdonordeste.api_raizes_do_nordeste.entity.User;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.CustomerRepository;
import br.com.raizesdonordeste.api_raizes_do_nordeste.repository.UserRepository;
import br.com.raizesdonordeste.api_raizes_do_nordeste.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;



    public LoginResponseDTO login(LoginRequestDTO dto, AuthenticationManager authenticationManager) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password()
                )
        );

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado"));

        String token = jwtService.generateToken(user);
        return new LoginResponseDTO(token, "Bearer");
    }

    @Transactional //para consistência
    public CustomerResponseDTO registerCustomer(CustomerRequestDTO dto) {
        User user = User.createCustomer(
                dto.email(),
                passwordEncoder.encode(dto.password())
        );
        userRepository.save(user);

        Customer customer = Customer.create(
                dto.name(),
                dto.telephone(),
                dto.address(),
                dto.lgpdConsent(),
                user
        );
        Customer savedCustomer = customerRepository.save(customer);

        String token = jwtService.generateToken(user);

        return new CustomerResponseDTO(
                savedCustomer.getId(),
                savedCustomer.getName(),
                user.getEmail(),
                savedCustomer.getTelephone(),
                savedCustomer.getAddress(),
                savedCustomer.getCreatedAt(),
                token,
                "Bearer"
        );
    }
}
