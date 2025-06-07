package com.safebank.demo.mappers;

import com.safebank.demo.domains.Account;
import com.safebank.demo.dtos.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements GenericMapper<Account, AccountDTO> {

    @Override
    public AccountDTO toDTO(Account account) {
        if (account == null) {
            return null;
        }
        return new AccountDTO(
                account.getNumber(),
                account.getBalance(),
                account.getCreditLimit()
        );
    }

    @Override
    public Account toEntity(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }
        Account account = new Account();
        account.setNumber(accountDTO.number());
        account.setBalance(accountDTO.balance());
        account.setCreditLimit(accountDTO.creditLimit());
        return account;
    }
}