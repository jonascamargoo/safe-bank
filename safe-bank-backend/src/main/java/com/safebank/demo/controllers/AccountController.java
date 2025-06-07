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
@CrossOrigin(origins = "http://localhost:4200")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/")
    public ResponseEntity<AccountDTO> createAccount(Authentication authentication) {
        AccountDTO createdAccount = accountService.createAccount(authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }

    @GetMapping("/")
    public ResponseEntity<List<AccountDTO>> listMyAccounts(Authentication authentication) {
        List<AccountDTO> accounts = accountService.getAccountsForAuthenticatedCustomer(authentication);
        return ResponseEntity.ok(accounts);
    }

    // @GetMapping("/")
    // public ResponseEntity<Page<AccountDTO>> listMyAccounts(
    //         Authentication authentication,
    //         @PageableDefault(size = 10) Pageable pageable) {
    //     Page<AccountDTO> accounts = accountService.getAccountsForAuthenticatedCustomer(authentication, pageable);
    //     return ResponseEntity.ok(accounts);
    // }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Void> deleteMyAccount(@PathVariable String accountNumber, Authentication authentication) {
        accountService.deleteAccount(accountNumber, authentication);
        return ResponseEntity.noContent().build();
    }

    
}
