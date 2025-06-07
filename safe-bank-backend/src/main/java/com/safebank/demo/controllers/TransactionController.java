package com.safebank.demo.controllers;

import com.safebank.demo.dtos.TransactionDTO;
import com.safebank.demo.dtos.TransferRequestDTO;
import com.safebank.demo.dtos.authentication.TransactionRequestDTO;
import com.safebank.demo.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposito")
    public ResponseEntity<Void> deposit(@RequestBody @Valid TransactionRequestDTO dto, Authentication authentication) {
        transactionService.deposit(dto, authentication);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/saque")
    public ResponseEntity<Void> withdraw(@RequestBody @Valid TransactionRequestDTO dto, Authentication authentication) {
        transactionService.withdraw(dto, authentication);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Void> transfer(@RequestBody @Valid TransferRequestDTO dto, Authentication authentication) {
        transactionService.transfer(dto, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/extrato/{accountNumber}")
    public ResponseEntity<List<TransactionDTO>> getStatement(@PathVariable String accountNumber, Authentication authentication) {
        List<TransactionDTO> statement = transactionService.getTransactionsByAccountNumber(accountNumber, authentication);
        return ResponseEntity.ok(statement);
    }
}