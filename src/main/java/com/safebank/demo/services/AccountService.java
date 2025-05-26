package com.safebank.demo.services;

import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.safebank.demo.domains.Account;
import com.safebank.demo.domains.Customer;
import com.safebank.demo.dtos.AccountDTO;
import com.safebank.demo.repositories.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService; 
    private final AccountDTO accountDTO;

    public AccountService(AccountRepository accountRepository, CustomerService customerService, AccountDTO accountDTO) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.accountDTO = accountDTO;

    }

    public Account createAccount(AccountDTO accountDTO) {
        Customer customer = customerService.getCustomerByCPF(accountDTO.customerCPF()); 
        Account newAccount = new Account();
        BeanUtils.copyProperties(customer, newAccount);
        newAccount.setNumber(generateNumber(accountDTO));
        return accountRepository.save(newAccount);
    }

    public String  generateNumber(AccountDTO accountDTO) {
        String twoFirstDigits = customerService
            .getFirstTwoDigits(accountDTO.customerCPF());
        Random random = new Random();
        String accountNumber;
        do {
            int randomDigits = 100000 + random.nextInt(900000);
            accountNumber = twoFirstDigits + "-" + randomDigits;
        } while (accountRepository.existsByNumber(accountNumber));
        return accountNumber;
    }



}
