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

    public AccountService(AccountRepository accountRepository, CustomerService customerService) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;

    }

    public Account createAccount(AccountDTO accountDTO) {
        String accountNumber = accountDTO.number();
        if(accountRepository.existsByNumber(accountNumber)) {
            throw new IllegalArgumentException("Account with number " + accountNumber + " already exists.");
        }
        Account account = new Account();
        BeanUtils.copyProperties(accountDTO, account);
        account.setNumber(generateNumber(accountDTO));
        return accountRepository.save(account);
    }

    public String  generateNumber(AccountDTO accountDTO) {
        Customer customer = customerService.getCustomerByCPF(accountDTO.customerCPF());
        String twoFirstDigits = customer.getName().substring(0, 2).toUpperCase();
        Random random = new Random();
        String accountNumber;
        do {
            int randomDigits = 100000 + random.nextInt(900000);
            accountNumber = twoFirstDigits + "-" + randomDigits;
        } while (accountRepository.existsByNumber(accountNumber));
        return accountNumber;
    }

}
