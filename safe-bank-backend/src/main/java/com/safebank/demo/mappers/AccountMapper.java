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
                account.getId(),
                account.getNumber(),
                account.getCustomer() != null ? account.getCustomer().getCpf() : null
        );
    }

    @Override
    public Account toEntity(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }
        Account account = new Account();
        account.setId(accountDTO.id());
        account.setNumber(accountDTO.number());
        return account;
    }
}