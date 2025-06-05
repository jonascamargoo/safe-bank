package com.safebank.demo.mappers;

import com.safebank.demo.domains.Customer;
import com.safebank.demo.dtos.CustomerDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements GenericMapper<Customer, CustomerDTO> {
    @Override
    public CustomerDTO toDTO(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getPhoneNumber()
        );
    }

    @Override
    public Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(customerDTO.id());
        customer.setName(customerDTO.name());
        customer.setCpf(customerDTO.cpf());
        return customer;
    }

}
