package com.safebank.demo.domains;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "tbConta")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    @NotBlank
    private String number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCliente", nullable = false)
    @NotNull
    private Customer customer;

    @NotNull(message = "Balance cannot be null.")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal balance;

    public Account() {
        this.balance = BigDecimal.ZERO;
    }

    // public Account(String number, Customer customer) {
    //     this(); // Calls the default constructor to set balance to ZERO
    //     this.number = number;
    //     this.customer = customer;
    // }
}