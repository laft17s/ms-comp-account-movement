package com.laft17s.mscompaccountmovement.services;

import com.laft17s.mscompaccountmovement.dto.AccountDTO;
import com.laft17s.mscompaccountmovement.entities.Account;
import com.laft17s.mscompaccountmovement.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountDTO findAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalStateException("Account not found"));
        return mapToAccountDTO(account);
    }

    private AccountDTO mapToAccountDTO(Account account) {
        // Mapping logic from Account entity to AccountDTO
        return AccountDTO.builder()
                .accountId(account.getAccountId())
                .accountNumber(account.getAccountNumber())
                .type(account.getType())
                .initialBalance(account.getInitialBalance())
                .status(account.getStatus())
                .clientId(account.getClientId())
                .build();
    }

}
