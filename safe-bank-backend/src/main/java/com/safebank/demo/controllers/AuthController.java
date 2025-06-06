package com.safebank.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safebank.demo.services.CustomerService;
import com.safebank.demo.services.security.TokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final CustomerService customerService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(CustomerService customerService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.customerService = customerService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }


}


