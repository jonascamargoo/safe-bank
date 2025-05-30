package com.safebank.demo.services;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.safebank.demo.domains.Account;
import com.safebank.demo.domains.Customer;
import com.safebank.demo.dtos.AccountDTO;
import com.safebank.demo.dtos.CustomerDTO;
import com.safebank.demo.mappers.AccountMapper;
import com.safebank.demo.mappers.CustomerMapper;
import com.safebank.demo.repositories.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService; 
    private final AccountMapper accountMapper;

    public AccountService(
        AccountRepository accountRepository, 
        CustomerService customerService, 
        AccountMapper accountMapper, 
        CustomerMapper customerMapper) {
            this.accountRepository = accountRepository;
            this.customerService = customerService;
            this.accountMapper = accountMapper;
    }

    @Transactional
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Customer customer = customerService.getCustomerEntityByCPF(accountDTO.customerCPF());
        Account account = new Account();
        account.setCustomer(customer);
        account.setNumber(generateNumber(accountDTO));
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDTO(savedAccount);
    }

    public String  generateNumber(AccountDTO accountDTO) {
        CustomerDTO customerDTO = customerService.getCustomerByCPF(accountDTO.customerCPF());
        String twoFirstDigits = customerDTO.name().substring(0, 2).toUpperCase();
        Random random = new Random();
        String accountNumber;
        do {
            int randomDigits = 100000 + random.nextInt(900000);
            accountNumber = twoFirstDigits + "-" + randomDigits;
        } while (accountRepository.existsByNumber(accountNumber));
        return accountNumber;
    }


    public Account getAccountByNumber(String number) {
        return accountRepository.findByNumber(number)
            .orElseThrow(() -> new IllegalArgumentException("Account not found with number: " + number));
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public boolean existsByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }


    @Transactional(readOnly = true)
    public List<AccountDTO> getAccountsByCustomerId(Long customerId) {
        if(!customerService.customerExistsById(customerId)) {
            throw new RuntimeException("Customer not found with ID: " + customerId);
        }

        List<Account> accounts = accountRepository.findByCustomer_Id(customerId);
        return accounts.stream()
            .map(accountMapper::toDTO)
            .collect(Collectors.toList());
    }

}
