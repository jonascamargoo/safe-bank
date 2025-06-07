package com.safebank.demo.dtos.authentication;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record TransactionRequestDTO(
    @NotBlank
    String accountNumber,

    @NotNull
    @Positive
    BigDecimal value
) {}