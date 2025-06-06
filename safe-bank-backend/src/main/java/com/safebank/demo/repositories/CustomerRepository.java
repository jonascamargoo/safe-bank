package com.safebank.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.safebank.demo.domains.Customer;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    UserDetails findByCpf(String cpf);

    // Novo método para retornar apenas o ID do Customer pelo CPF
    @Query("SELECT c.id FROM Customer c WHERE c.cpf = :cpfValue")
    Optional<Long> findIdByCpf(@Param("cpfValue") String cpf);
    
}




