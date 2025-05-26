package com.safebank.demo.services;

import org.springframework.stereotype.Service;

import com.safebank.demo.domains.Customer;
import com.safebank.demo.repositories.CustomerRepository;

@Service
public class CustomerService {
    
    private final AccountService accountService;
    private final CustomerRepository customerRepository;
    
    public CustomerService(AccountService accountService, CustomerRepository customerRepository) {
        this.accountService = accountService;
        this.customerRepository = customerRepository;

    }

    public Customer getCustomerByCPF(String CPF) {
        return customerRepository.findByCPF(CPF)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com CPF: " + CPF));
    }

    public String getFirstTwoDigits(String CPF) {
        Customer customer = getCustomerByCPF(CPF);
        return  customer.getName().substring(0, 2).toUpperCase();
    }



}
