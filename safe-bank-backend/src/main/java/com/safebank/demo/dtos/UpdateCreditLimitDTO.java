package com.safebank.demo.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record UpdateCreditLimitDTO(
    @NotNull(message = "O novo limite não pode ser nulo.")
    @PositiveOrZero(message = "O limite de crédito deve ser um valor positivo ou zero.")
    BigDecimal newCreditLimit
) {}