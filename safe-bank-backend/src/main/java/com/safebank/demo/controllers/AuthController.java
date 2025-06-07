package com.safebank.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safebank.demo.domains.Customer;
import com.safebank.demo.dtos.authentication.LoginRequestDTO;
import com.safebank.demo.dtos.authentication.LoginResponseDTO;
import com.safebank.demo.dtos.authentication.RegisterRequestDTO;
import com.safebank.demo.dtos.authentication.RegisterResponseDTO;
import com.safebank.demo.services.CustomerService;
import com.safebank.demo.services.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private final CustomerService customerService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(CustomerService customerService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.customerService = customerService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registrar")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody @Valid RegisterRequestDTO registerRequest) {
        try {
            customerService.registerCustomer(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponseDTO("Cliente cadastrado com sucesso"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RegisterResponseDTO(e.getMessage()));
        }
    }

    @PostMapping("/logar")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.cpf(), loginRequest.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Customer) auth.getPrincipal());
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token));
    }

}


