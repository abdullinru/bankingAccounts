package com.github.abdullinru.bankingAccounts.service;

import com.github.abdullinru.bankingAccounts.dto.DepositDto;
import com.github.abdullinru.bankingAccounts.dto.ResponseAccountDto;
import com.github.abdullinru.bankingAccounts.dto.WithdrawDto;
import com.github.abdullinru.bankingAccounts.exception.AccountNotFoundException;
import com.github.abdullinru.bankingAccounts.mapper.AccountMapper;
import com.github.abdullinru.bankingAccounts.model.Account;
import com.github.abdullinru.bankingAccounts.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BalanceService {
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    public BalanceService(AccountRepository accountRepository, AccountMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }
    public ResponseAccountDto deposit(DepositDto depositDto) {
        chechAmount(depositDto.getAmount());
        Account findAccount = accountRepository
                .findById(depositDto.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("account is not found"));
        findAccount.setBalance(findAccount.getBalance().add(depositDto.getAmount()));
        accountRepository.save(findAccount);
        ResponseAccountDto response = mapper.toResponseAccountDto(findAccount);
        return response;
    }

    private void chechAmount(BigDecimal amount) {
        if (amount.signum() == -1) {
            throw new IllegalArgumentException("incorrect value of deposit");
        }
    }

    public ResponseAccountDto withdraw(WithdrawDto withdrawDto) {
        chechAmount(withdrawDto.getAmount());
        checkPin(withdrawDto.getPinCode());
        Account findAccount = accountRepository
                .findById(withdrawDto.getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account is not found"));

        comparePinCodes(withdrawDto.getPinCode(), findAccount.getPinCode());
        if (findAccount.getBalance().compareTo(withdrawDto.getAmount()) < 0) {
            throw new IllegalArgumentException("not enouht money for withdraw");
        }
        findAccount.setBalance(findAccount.getBalance().subtract(withdrawDto.getAmount()));
        accountRepository.save(findAccount);
        ResponseAccountDto response = mapper.toResponseAccountDto(findAccount);
        return response;
    }
    private void checkPin(String pinCode) {
        if (!pinCode.matches("\\d{4}")) {
            throw new IllegalArgumentException("incorrect pincode");
        }
    }

    private void comparePinCodes(String pin1, String pin2) {
        if (!pin1.equals(pin2)) {
            throw new IllegalArgumentException("PIN codes do not match");
        }
    }
}
