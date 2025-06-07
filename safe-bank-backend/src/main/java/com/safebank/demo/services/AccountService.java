package com.safebank.demo.services;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.core.Authentication;

import com.safebank.demo.domains.Account;
import com.safebank.demo.domains.Customer;
import com.safebank.demo.dtos.AccountDTO;
import com.safebank.demo.dtos.CustomerDTO;
import com.safebank.demo.exceptions.customExceptions.AccountNotFound;
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

    // @Transactional
    // public AccountDTO createAccount(AccountDTO accountDTO) {
    //     Customer customer = customerService.getCustomerEntityByCpf(accountDTO.customerCPF());
    //     Account account = new Account();
    //     account.setCustomer(customer);
    //     account.setNumber(generateNumber(accountDTO));
    //     Account savedAccount = accountRepository.save(account);
    //     return accountMapper.toDTO(savedAccount);
    // }

    // public String  generateNumber(AccountDTO accountDTO) {
    //     CustomerDTO customerDTO = customerService.getCustomerByCpf(accountDTO.customerCPF());
    //     String twoFirstDigits = customerDTO.name().substring(0, 2).toUpperCase();
    //     Random random = new Random();
    //     String accountNumber;
    //     do {
    //         int randomDigits = 100000 + random.nextInt(900000);
    //         accountNumber = twoFirstDigits + "-" + randomDigits;
    //     } while (accountRepository.existsByNumber(accountNumber));
    //     return accountNumber;
    // }

    @Transactional
    public AccountDTO createAccount(Authentication authentication) {
        // 1. Pega o usuário logado diretamente do objeto de autenticação.
        Customer customer = (Customer) authentication.getPrincipal();

        // 2. Cria a nova conta
        Account account = new Account();
        account.setCustomer(customer); // Usa o cliente autenticado
        account.setNumber(generateNumber(customer)); // Passa o objeto Customer
        Account savedAccount = accountRepository.save(account);

        return accountMapper.toDTO(savedAccount);
    }

    // Altere o método generateNumber para receber um Customer em vez de um AccountDTO
    public String generateNumber(Customer customer) {
        String twoFirstDigits = customer.getName().substring(0, 2).toUpperCase();
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
            .orElseThrow(AccountNotFound::new);
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    public boolean existsByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }


    // @Transactional(readOnly = true)
    // public List<AccountDTO> getAccountsByCustomerId(Long customerId) {
    //     if(!customerService.customerExistsById(customerId)) {
    //         throw new RuntimeException("Customer not found with ID: " + customerId);
    //     }

    //     List<Account> accounts = accountRepository.findByCustomer_Id(customerId);
    //     return accounts.stream()
    //         .map(accountMapper::toDTO)
    //         .collect(Collectors.toList());
    // }

    @Transactional
    public void deleteAccount(String accountNumber, Authentication authentication) {
        // 1. Pega o usuário que está autenticado via token.
        Customer authenticatedCustomer = (Customer) authentication.getPrincipal();

        // 2. Busca a conta no banco de dados pelo número fornecido.
        // Se não encontrar, lança a exceção AccountNotFound.
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(AccountNotFound::new);

        // 3. VERIFICAÇÃO DE POSSE (PASSO MAIS IMPORTANTE)
        // Compara o ID do dono da conta com o ID do usuário que está logado.
        if (!account.getCustomer().getId().equals(authenticatedCustomer.getId())) {
            // Se os IDs forem diferentes, o usuário não é o dono da conta.
            // Lançamos uma exceção para negar o acesso.
            throw new SecurityException("Acesso negado: você não tem permissão para remover esta conta.");
        }

        // 4. Se a verificação de posse passar, deleta a conta.
        accountRepository.delete(account);
    }

    @Transactional(readOnly = true)
    public List<AccountDTO> getAccountsForAuthenticatedCustomer(Authentication authentication) {
        // 1. Pega o usuário logado a partir do token
        Customer authenticatedCustomer = (Customer) authentication.getPrincipal();

        // 2. Busca no repositório as contas que pertencem a esse usuário
        List<Account> accounts = accountRepository.findByCustomer_Id(authenticatedCustomer.getId());

        // 3. Mapeia para DTO e retorna a lista
        return accounts.stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }


}
