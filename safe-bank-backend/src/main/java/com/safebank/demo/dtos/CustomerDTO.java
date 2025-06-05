package com.safebank.demo.dtos;

public record CustomerDTO(
    Long id,
    String name,
    String cpf,
    String phoneNumber
) {}
