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


}