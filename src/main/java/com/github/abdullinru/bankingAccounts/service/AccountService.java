package com.github.abdullinru.bankingAccounts.service;

import com.github.abdullinru.bankingAccounts.dto.*;
import com.github.abdullinru.bankingAccounts.mapper.AccountMapper;
import com.github.abdullinru.bankingAccounts.model.Account;
import com.github.abdullinru.bankingAccounts.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    public AccountService(AccountRepository accountRepository, AccountMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }

    public List<ResponseAccountDto> getAllAccounts() {

        List<Account> accounts = accountRepository.findAll();
        return mapper.toListResponseAccountDto(accounts);
    }

    public ResponseAccountDto createAccount(RequestAccountDto accountDto) {
        checkBalance(accountDto.balance());
        checkPin(accountDto.pinCode());
        Account createAccount = mapper.toAccount(accountDto);
        accountRepository.save(createAccount);
        ResponseAccountDto response = mapper.toResponseAccountDto(createAccount);
        return response;
    }

    private void checkPin(String pinCode) {
        if (!pinCode.matches("\\d{4}")) {
            throw new IllegalArgumentException("incorrect pincode");
        }
    }

    private void checkBalance(BigDecimal balance) {
        if (balance.signum() == -1) {
            throw new IllegalArgumentException("Balance is less then 0");
        }
    }
}
