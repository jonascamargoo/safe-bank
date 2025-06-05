package com.safebank.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.safebank.demo.domains.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    UserDetails findByCpf(String CPF);
}




