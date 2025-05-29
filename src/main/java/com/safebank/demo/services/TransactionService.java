package com.safebank.demo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safebank.demo.domains.Transaction;
import com.safebank.demo.dtos.TransactionDTO;
import com.safebank.demo.repositories.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public List<TransactionDTO> getTransactionsByAccountNumber(String accountNumber) {
        if(!accountService.existsByNumber(accountNumber)) {
            throw new Error("Account not found with number: " + accountNumber);
        }
        List<Transaction> transactions = transactionRepository.findByAccount_NumberOrderByTransactionDateDesc(accountNumber);
        return transactions.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }

    // farei o mapeamento apenas por aqui? Se não, devo abstrair isso para uma classe utilitária
    private TransactionDTO toDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getValue(),
                transaction.getType(),
                transaction.getTransactionDate(),
                transaction.getAccount().getNumber()
        );
    }

}