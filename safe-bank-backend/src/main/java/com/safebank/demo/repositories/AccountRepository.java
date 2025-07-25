package com.safebank.demo.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.safebank.demo.domains.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByNumber(String number);
    List<Account> findByCustomerCpf(String customerCPF);
    boolean existsByNumber(String number);
    List<Account> findByCustomer_Id(Long customerId);
    // comecei a fazer paginacao, mas não era necessário a priori
    // Page<Account> findByCustomer_Id(Long customerId, Pageable pageable);

    @Query("SELECT SUM(a.balance) FROM Account a WHERE a.customer.id = :customerId")
    BigDecimal sumBalanceByCustomerId(@Param("customerId") Long customerId);

}
