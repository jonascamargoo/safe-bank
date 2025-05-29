package com.safebank.demo.repositories;

import com.safebank.demo.domains.Transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // List<Transaction> findByAccountIdOrderByTransactionDateDesc(Long accountId);

    // ---------------------------------

    // SQL equivalente
    // SELECT t.*
    // FROM tbLancamento t
    // JOIN tbConta a ON t.idConta = a.id
    // WHERE a.numero = 'valor_do_accountNumber_aqui'
    // ORDER BY t.transaction_date DESC;
    List<Transaction> findByAccount_NumberOrderByTransactionDateDesc(String accountNumber);



}