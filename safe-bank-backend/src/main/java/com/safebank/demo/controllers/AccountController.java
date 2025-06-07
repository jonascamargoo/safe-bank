package com.safebank.demo.controllers;

import com.safebank.demo.dtos.AccountDTO;
import com.safebank.demo.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/")
    public ResponseEntity<AccountDTO> createAccount(Authentication authentication) {
        // Passamos o objeto de autenticação para o serviço
        AccountDTO createdAccount = accountService.createAccount(authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    // @GetMapping("/clientes/{customerId}/contas")
    // public ResponseEntity<List<AccountDTO>> listAccountsForCustomer(@PathVariable Long customerId) {
    //     List<AccountDTO> accounts = accountService.getAccountsByCustomerId(customerId);
    //     return ResponseEntity.ok(accounts);
    // }

    @GetMapping("/")
    public ResponseEntity<List<AccountDTO>> listMyAccounts(Authentication authentication) {
        List<AccountDTO> accounts = accountService.getAccountsForAuthenticatedCustomer(authentication);
        return ResponseEntity.ok(accounts);
    }
}
