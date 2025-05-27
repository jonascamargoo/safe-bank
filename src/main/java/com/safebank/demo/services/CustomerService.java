package com.safebank.demo.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.safebank.demo.domains.Customer;
import com.safebank.demo.dtos.CustomerDTO;
import com.safebank.demo.repositories.CustomerRepository;

@Service
public class CustomerService {
    
    private final AccountService accountService;
    private final CustomerRepository customerRepository;
    
    public CustomerService(AccountService accountService, CustomerRepository customerRepository) {
        this.accountService = accountService;
        this.customerRepository = customerRepository;

    }

    public Customer createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customerRepository.save(customer);
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
    }

    public Customer getCustomerByCPF(String CPF) {
        return customerRepository.findByCPF(CPF)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com CPF: " + CPF));
    }

    public String getFirstTwoDigits(String CPF) {
        Customer customer = getCustomerByCPF(CPF);
        return  customer.getName().substring(0, 2).toUpperCase();
    }

    public void deleteCustomer(Long id) {
        customerRepository.delete(getCustomerById(id));
    }


}
