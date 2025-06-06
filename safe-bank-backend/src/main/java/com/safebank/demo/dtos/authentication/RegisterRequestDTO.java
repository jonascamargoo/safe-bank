package com.safebank.demo.dtos.authentication;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(
    @NotBlank String cpf,
    @NotBlank String password
) {}
