package com.safebank.demo.mappers;

import com.safebank.demo.domains.Transaction;
import com.safebank.demo.dtos.TransactionDTO;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements GenericMapper<Transaction, TransactionDTO> {
    @Override
    public TransactionDTO toDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        Long originId = transaction.getOriginTransaction() != null ? transaction.getOriginTransaction().getId() : null;
        return new TransactionDTO(
                transaction.getId(),
                transaction.getValue(),
                transaction.getType(),
                transaction.getTransactionDate(),
                transaction.getAccount() != null ? transaction.getAccount().getNumber() : null,
                originId
        );
    }

    @Override
    public Transaction toEntity(TransactionDTO transactionDTO) {
        if (transactionDTO == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(transactionDTO.id());
        transaction.setValue(transactionDTO.value());
        return transaction;
    }

}
