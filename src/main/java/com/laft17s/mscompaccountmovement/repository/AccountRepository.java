package com.laft17s.mscompaccountmovement.repository;

import com.laft17s.mscompaccountmovement.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository  extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
}
