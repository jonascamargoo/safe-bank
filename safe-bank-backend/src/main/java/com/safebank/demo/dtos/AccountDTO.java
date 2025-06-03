package com.safebank.demo.dtos;

public record AccountDTO(
    Long id,
    String number,
    String customerCPF
) {}