package com.safebank.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safebank.demo.domains.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNumber(String number);
    List<Account> findByCustomerCPF(String customerCPF);
    boolean existsByNumber(String number);
    List<Account> findByCustomer_Id(Long customerId);
}
