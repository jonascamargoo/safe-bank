package com.safebank.demo.dtos;


import java.math.BigDecimal;

// Altere os campos para refletir o que você quer retornar
public record AccountDTO(
    String number,
    BigDecimal balance,
    BigDecimal creditLimit
) {}