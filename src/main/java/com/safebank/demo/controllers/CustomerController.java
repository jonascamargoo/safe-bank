package com.safebank.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safebank.demo.domains.Customer;
import com.safebank.demo.dtos.CustomerDTO;
import com.safebank.demo.services.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class CustomerController {
    private final CustomerService customerService;

    public  CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    
    @GetMapping("/")
    public ResponseEntity<?> getCustomers() {
        List<Customer> customers = customerService.getCustomers();
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable(value = "id") Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(customer);

    }

    @PostMapping("/")
    public ResponseEntity<Customer> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        Customer createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable(value = "id") Long id) {
        this.customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cliente removido com sucesso.");
    }


}
