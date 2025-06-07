package com.safebank.demo.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record TransferRequestDTO(
    @NotBlank String sourceAccountNumber,
    @NotBlank String destinationAccountNumber,
    @NotNull @Positive BigDecimal value
) {}