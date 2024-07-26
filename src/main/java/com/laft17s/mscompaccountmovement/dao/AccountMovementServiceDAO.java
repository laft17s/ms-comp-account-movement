package com.laft17s.mscompaccountmovement.dao;

import com.laft17s.mscompaccountmovement.dto.AccountDTO;
import com.laft17s.mscompaccountmovement.dto.MovementDTO;
import com.laft17s.mscompaccountmovement.entities.Account;
import com.laft17s.mscompaccountmovement.mappers.AccountMapper;
import com.laft17s.mscompaccountmovement.mappers.MovementMapper;
import com.laft17s.mscompaccountmovement.repository.AccountRepository;
import com.laft17s.mscompaccountmovement.repository.MovementRepository;
import com.laft17s.mscompaccountmovement.services.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountMovementServiceDAO {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private MovementMapper movementMapper;

    public List<AccountDTO> filterAccount(AccountDTO request) {
        Account data = accountMapper.toAcount(request);
        return accountMapper.toListAccountDTO(accountRepository.findAll());
    }

    public List<AccountDTO> listAccounts() {
        return accountMapper.toListAccountDTO(accountRepository.findAll());
    }

    public List<MovementDTO> listMovements() {
        return movementMapper.toListMovementDTO(movementRepository.findAll());
    }

    public void saveAccount (AccountDTO request) {
        accountRepository.save(accountMapper.toAcount(request));
    }

    @Transactional
    public void saveMovement (MovementDTO request) {
        movementRepository.save(movementMapper.toMovement(request));
    }

    public AccountDTO findByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalStateException("Cuenta no válida."));
        return accountMapper.toAcountDTO(account);
    }


}
