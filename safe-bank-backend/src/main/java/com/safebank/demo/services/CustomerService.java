package com.safebank.demo.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.safebank.demo.domains.Customer;
import com.safebank.demo.dtos.CustomerDTO;
import com.safebank.demo.mappers.CustomerMapper;
import com.safebank.demo.repositories.CustomerRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDTO(savedCustomer);
    }

    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
        return customerMapper.toDTO(customer);
    }

    public CustomerDTO getCustomerByCPF(String CPF) {
        return customerRepository.findByCPF(CPF)
                .map(customerMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com CPF: " + CPF));
    }
    

    public Customer getCustomerEntityByCPF(String CPF) {
        return customerRepository.findByCPF(CPF)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com CPF: " + CPF));
    }


    public boolean customerExistsById(Long id) {
        return customerRepository.existsById(id);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id + " para exclusão.");
        }
        customerRepository.deleteById(id);

    }
}