package com.safebank.demo.dtos;

public record CustomerDTO(
    Long id,
    String name,
    String CPF,
    String phoneNumber
) {}
