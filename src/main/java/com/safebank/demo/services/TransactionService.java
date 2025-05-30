package com.safebank.demo.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safebank.demo.domains.Account;
import com.safebank.demo.domains.Transaction;
import com.safebank.demo.domains.TransactionType;
import com.safebank.demo.dtos.TransactionDTO;
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

    public List<TransactionDTO> getTransactionsByAccountNumber(String accountNumber) {
        if(!accountService.existsByNumber(accountNumber)) {
            throw new Error("Account not found with number: " + accountNumber);
        }
        List<Transaction> transactions = transactionRepository.findByAccount_NumberOrderByTransactionDateDesc(accountNumber);
        return transactions.stream()
                .map(transactionMapper::toDTO)
                .collect(Collectors.toList());

    }

    @Transactional
    public TransactionDTO deposit(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        Account account = accountService.getAccountByNumber(accountNumber);
        account.setBalance(account.getBalance().add(amount));
        accountService.saveAccount(account);
        Transaction transaction = new Transaction(account, amount, TransactionType.DEPOSIT);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toDTO(savedTransaction);
    }

    @Transactional
    public TransactionDTO withdraw(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }

        Account account = accountService.getAccountByNumber(accountNumber);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new Error("Insufficient funds for withdrawal from account: " + accountNumber);
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountService.saveAccount(account);

        Transaction transaction = new Transaction(account, amount, TransactionType.WITHDRAWAL);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toDTO(savedTransaction);
    }


}