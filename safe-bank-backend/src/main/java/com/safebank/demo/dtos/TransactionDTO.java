package com.safebank.demo.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.safebank.demo.domains.TransactionType;

public record TransactionDTO(
    Long id,
    BigDecimal value,
    TransactionType type,
    LocalDateTime transactionDate,
    String accountNumber,
    Long originTransactionId
) {}