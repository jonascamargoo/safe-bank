package com.safebank.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.safebank.demo.domains.Customer;
import com.safebank.demo.dtos.CustomerDTO;
import com.safebank.demo.dtos.authentication.RegisterRequestDTO;
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

    public CustomerDTO getAuthenticatedCustomerInfo(Authentication authentication) {
        // 1. Pega o objeto do usuário que foi autenticado via token
        Customer authenticatedCustomer = (Customer) authentication.getPrincipal();

        // 2. Mapeia a entidade Customer para um CustomerDTO para não expor a senha
        return customerMapper.toDTO(authenticatedCustomer);
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

    public Customer registerCustomer(RegisterRequestDTO registerRequest) {
        String customerCpf = registerRequest.cpf();

        if(customerRepository.findByCpf(registerRequest.cpf()) != null) {
            throw new RuntimeException("Cliente já cadastrado com CPF: " + customerCpf);
        }

        String encryptedPassword = new BCryptPasswordEncoder()
                .encode(registerRequest.password());

        Customer newCustomer = new Customer(
            registerRequest.name(),
            registerRequest.cpf(),
            encryptedPassword,
            registerRequest.phoneNumber()
        );
        
        // Customer newCustomer = new Customer(customerCpf, encryptedPassword);

        return customerRepository.save(newCustomer);

    }


    // public void registerCustomer(RegisterRequestDTO registerRequest) {
    //     if (customerRepository.findByCpf(registerRequest.cpf()) != null) {
    //         throw new IllegalArgumentException("CPF já cadastrado.");
    //     }
        
    //     // A LINHA ABAIXO É O PONTO CRÍTICO
    //     Customer newCustomer = customerMapper.toEntity(registerRequest);

    //     String encryptedPassword = passwordEncoder.encode(registerRequest.password());
    //     newCustomer.setPassword(encryptedPassword);

    //     customerRepository.save(newCustomer);
    // }

    // public CustomerDTO getCustomerByCpf(String cpf) {
    //     return customerRepository.findByCpf(cpf)
    //             .map(customerMapper::toDTO)
    //             .orElseThrow(() -> new RuntimeException("Cliente não encontrado com CPF: " + CPF));
    // }

    public CustomerDTO getCustomerByCpf(String cpf) {
        UserDetails userDetails = customerRepository.findByCpf(cpf);
        // 1. Usa o método privado para fazer a conversão
        return toCustomer(userDetails)
                // 2. Mapeia o Customer (se existir) para DTO
                .map(customerMapper::toDTO)
                // 3. Lança uma exceção se o Optional estiver vazio
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com CPF: " + cpf));
    }
    

    public Customer getCustomerEntityByCpf(String cpf) {
        UserDetails customer = customerRepository.findByCpf(cpf);
        return toCustomer(customer).get();
    }

    // public Customer getCustomerEntityByCpf(String cpf) {
    //     return customerRepository.findByCpf(cpf)
    //             .orElseThrow(() -> new RuntimeException("Cliente não encontrado com CPF: " + CPF));
    // }

    public Long getCustomerIdByCpf(String cpf) {
        return customerRepository.findIdByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com CPF: " + cpf));
    }


    public boolean customerExistsById(Long id) {
        return customerRepository.existsById(id);
    }

    private Optional<Customer> toCustomer(UserDetails userDetails) {
        return Optional.ofNullable(userDetails)
                .filter(Customer.class::isInstance)
                .map(Customer.class::cast);
    }
}