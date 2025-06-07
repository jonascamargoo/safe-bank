package com.safebank.demo.domains;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "tbLancamento")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Transaction value cannot be null.")
    @DecimalMin(value = "0.01", message = "Transaction value must be positive.")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal value;

    @NotNull(message = "Transaction must be associated with an account.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idConta", nullable = false) // idConta é a FK na tabela de Lançamentos
    private Account account;

    @NotNull(message = "Transaction type cannot be null.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionType type;

    @NotNull(message = "Transaction date cannot be null.")
    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idLancamentoOrigem")
    private Transaction originTransaction;

    public Transaction() {
        this.transactionDate = LocalDateTime.now();
    }

    public Transaction(Account account, BigDecimal value, TransactionType type) {
        this(); // usando o construtor padrão para definir a data da transação
        this.account = account;
        this.value = value;
        this.type = type;

    }

   
}