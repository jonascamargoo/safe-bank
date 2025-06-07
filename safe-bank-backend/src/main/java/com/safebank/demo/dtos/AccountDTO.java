package com.safebank.demo.dtos;


import java.math.BigDecimal;

public record AccountDTO(
    String number,
    BigDecimal balance,
    BigDecimal creditLimit
) {}