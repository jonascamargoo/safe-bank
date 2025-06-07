package com.safebank.demo.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;

import com.safebank.demo.domains.Account;
import com.safebank.demo.domains.Customer;
import com.safebank.demo.domains.Transaction;
import com.safebank.demo.domains.TransactionType;
import com.safebank.demo.dtos.TransactionDTO;
import com.safebank.demo.dtos.authentication.TransactionRequestDTO;
import com.safebank.demo.mappers.TransactionMapper;
import com.safebank.demo.repositories.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;
    
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.transactionMapper = transactionMapper;
    }

    @Transactional(readOnly = true)
    public List<TransactionDTO> getTransactionsByAccountNumber(String accountNumber, Authentication authentication) {
        // Busca a conta e verifica se o usuário autenticado é o dono
        Account account = checkAccountOwnership(accountNumber, authentication);
        
        List<Transaction> transactions = transactionRepository.findByAccount_NumberOrderByTransactionDateDesc(account.getNumber());
        return transactions.stream()
            .map(transactionMapper::toDTO)
            .toList();
    }

    @Transactional
    public void deposit(TransactionRequestDTO dto, Authentication authentication) {
        // Busca a conta e verifica se o usuário autenticado é o dono
        Account account = checkAccountOwnership(dto.accountNumber(), authentication);

        BigDecimal value = dto.value();
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Error("O valor do depósito deve ser positivo.");
        }
        account.setBalance(account.getBalance().add(value));
        accountService.saveAccount(account);
        Transaction transaction = new Transaction(account, value, TransactionType.DEPOSIT);
        transactionRepository.save(transaction);
    }

    @Transactional
    public void withdraw(TransactionRequestDTO dto, Authentication authentication) {
        // Busca a conta e verifica se o usuário autenticado é o dono
        Account account = checkAccountOwnership(dto.accountNumber(), authentication);
        
        BigDecimal value = dto.value();
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Error("O valor do saque deve ser positivo.");
        }
        if (account.getBalance().compareTo(value) < 0) {
            throw new Error("Saldo insuficiente para realizar o saque.");
        }
        account.setBalance(account.getBalance().subtract(value));
        accountService.saveAccount(account);
        Transaction transaction = new Transaction(account, value, TransactionType.WITHDRAWAL);
        transactionRepository.save(transaction);
    }
    


        // --- NOVO MÉTODO PRIVADO PARA VERIFICAÇÃO DE SEGURANÇA ---
    private Account checkAccountOwnership(String accountNumber, Authentication authentication) {
        Customer authenticatedCustomer = (Customer) authentication.getPrincipal();
        Account account = accountService.getAccountByNumber(accountNumber);

        if (!account.getCustomer().getId().equals(authenticatedCustomer.getId())) {
            throw new SecurityException("Acesso negado: esta conta não pertence a você.");
        }
        return account;
    }
}













