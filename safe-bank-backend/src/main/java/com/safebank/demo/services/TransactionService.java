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
import com.safebank.demo.dtos.TransferRequestDTO;
import com.safebank.demo.dtos.authentication.TransactionRequestDTO;
import com.safebank.demo.mappers.TransactionMapper;
import com.safebank.demo.repositories.AccountRepository;
import com.safebank.demo.repositories.TransactionRepository;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;
    
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, TransactionMapper transactionMapper, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.transactionMapper = transactionMapper;
        this.accountRepository = accountRepository;
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
        Account account = checkAccountOwnership(dto.accountNumber(), authentication);
        Customer customer = (Customer) authentication.getPrincipal();

        BigDecimal value = dto.value();
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Error("O valor do depósito deve ser positivo.");
        }

        BigDecimal totalBalance = accountRepository.sumBalanceByCustomerId(customer.getId());
        if (totalBalance == null) {
            totalBalance = BigDecimal.ZERO;
        }

        BigDecimal finalValue = value;
        if (value.compareTo(totalBalance) > 0) {
            BigDecimal bonus = value.multiply(new BigDecimal("0.10"));
            finalValue = value.add(bonus);
        }

        account.setBalance(account.getBalance().add(finalValue));
        accountService.saveAccount(account);

        Transaction transaction = new Transaction(account, finalValue, TransactionType.DEPOSIT);
        transactionRepository.save(transaction);
    }

    // @Transactional
    // public void deposit(TransactionRequestDTO dto, Authentication authentication) {
    //     // Busca a conta e verifica se o usuário autenticado é o dono
    //     Account account = checkAccountOwnership(dto.accountNumber(), authentication);

    //     BigDecimal value = dto.value();
    //     if (value.compareTo(BigDecimal.ZERO) <= 0) {
    //         throw new Error("O valor do depósito deve ser positivo.");
    //     }
    //     account.setBalance(account.getBalance().add(value));
    //     accountService.saveAccount(account);
    //     Transaction transaction = new Transaction(account, value, TransactionType.DEPOSIT);
    //     transactionRepository.save(transaction);
    // }

    @Transactional
    public void withdraw(TransactionRequestDTO dto, Authentication authentication) {
        Account account = checkAccountOwnership(dto.accountNumber(), authentication);
        
        BigDecimal value = dto.value();
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Error("O valor do saque deve ser positivo.");
        }
        
        BigDecimal availableFounds = account.getBalance().add(account.getCreditLimit());

        if (availableFounds.compareTo(value) < 0) {
            throw new Error("Saldo e limite de crédito insuficientes para realizar o saque.");
        }

        account.setBalance(account.getBalance().subtract(value));
        accountService.saveAccount(account);

        Transaction transaction = new Transaction(account, value, TransactionType.WITHDRAWAL);
        transactionRepository.save(transaction);
    }

    // @Transactional
    // public void withdraw(TransactionRequestDTO dto, Authentication authentication) {
    //     // Busca a conta e verifica se o usuário autenticado é o dono
    //     Account account = checkAccountOwnership(dto.accountNumber(), authentication);
        
    //     BigDecimal value = dto.value();
    //     if (value.compareTo(BigDecimal.ZERO) <= 0) {
    //         throw new Error("O valor do saque deve ser positivo.");
    //     }
    //     if (account.getBalance().compareTo(value) < 0) {
    //         throw new Error("Saldo insuficiente para realizar o saque.");
    //     }
    //     account.setBalance(account.getBalance().subtract(value));
    //     accountService.saveAccount(account);
    //     Transaction transaction = new Transaction(account, value, TransactionType.WITHDRAWAL);
    //     transactionRepository.save(transaction);
    // }

    @Transactional
    public void transfer(TransferRequestDTO dto, Authentication authentication) {
        if (dto.sourceAccountNumber().equals(dto.destinationAccountNumber())) {
            throw new IllegalArgumentException("A conta de origem não pode ser a mesma que a de destino.");
        }

        Account sourceAccount = checkAccountOwnership(dto.sourceAccountNumber(), authentication);
        Account destinationAccount = accountService.getAccountByNumber(dto.destinationAccountNumber());

        BigDecimal value = dto.value();
        BigDecimal totalDebit = value;
        BigDecimal fee = BigDecimal.ZERO;

        // Verifica se a transferência eh entre clientes diferentes
        if (!sourceAccount.getCustomer().getId().equals(destinationAccount.getCustomer().getId())) {
            fee = value.multiply(new BigDecimal("0.10"));
            totalDebit = value.add(fee);
        }

        if (sourceAccount.getBalance().add(sourceAccount.getCreditLimit()).compareTo(totalDebit) < 0) {
            throw new Error("Saldo e limite de crédito insuficientes para a transferência.");
        }

        // Debita da conta de origem
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(totalDebit));
        accountService.saveAccount(sourceAccount);

        // Credita na conta de destino
        destinationAccount.setBalance(destinationAccount.getBalance().add(value));
        accountService.saveAccount(destinationAccount);

        // Registra a transação de transferência (saída)
        Transaction transferTransaction = new Transaction(sourceAccount, value, TransactionType.TRANSFER);
        transactionRepository.save(transferTransaction);

        // Registra a transação de transferência (entrada) para a conta de destino
         Transaction destinationTransaction = new Transaction(destinationAccount, value, TransactionType.TRANSFER);
        transactionRepository.save(destinationTransaction);


        // Registra a taxa se aplicável
        if (fee.compareTo(BigDecimal.ZERO) > 0) {
            Transaction feeTransaction = new Transaction(sourceAccount, fee, TransactionType.FEE);
            feeTransaction.setOriginTransaction(transferTransaction);
            transactionRepository.save(feeTransaction);
        }
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













