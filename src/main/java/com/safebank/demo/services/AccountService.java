package com.safebank.demo.services;

import org.springframework.stereotype.Service;

import com.safebank.demo.repositories.AccountRepository;
import com.safebank.demo.repositories.CustomerRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;   

    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;

    }
}
