package com.github.abdullinru.bankingAccounts.service;

import com.github.abdullinru.bankingAccounts.dto.DepositDto;
import com.github.abdullinru.bankingAccounts.dto.ResponseAccountDto;
import com.github.abdullinru.bankingAccounts.dto.WithdrawDto;
import com.github.abdullinru.bankingAccounts.exception.AccountNotFoundException;
import com.github.abdullinru.bankingAccounts.mapper.AccountMapper;
import com.github.abdullinru.bankingAccounts.model.Account;
import com.github.abdullinru.bankingAccounts.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BalanceService {
    private final AccountRepository accountRepository;
    private final AccountMapper mapper;

    public BalanceService(AccountRepository accountRepository, AccountMapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseAccountDto deposit(DepositDto depositDto) {
        chechAmount(depositDto.amount());
        Account findAccount = accountRepository
                .findById(depositDto.accountId())
                .orElseThrow(() -> new AccountNotFoundException("account is not found"));
        findAccount.setBalance(findAccount.getBalance().add(depositDto.amount()));
        accountRepository.save(findAccount);
        ResponseAccountDto response = mapper.toResponseAccountDto(findAccount);
        return response;
    }

    private void chechAmount(BigDecimal amount) {
        if (amount.signum() == -1) {
            throw new IllegalArgumentException("incorrect value of deposit");
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseAccountDto withdraw(WithdrawDto withdrawDto) {
        chechAmount(withdrawDto.amount());
        checkPin(withdrawDto.pinCode());
        Account findAccount = accountRepository
                .findById(withdrawDto.accountId())
                .orElseThrow(() -> new AccountNotFoundException("Account is not found"));

        comparePinCodes(withdrawDto.pinCode(), findAccount.getPinCode());
        if (findAccount.getBalance().compareTo(withdrawDto.amount()) < 0) {
            throw new IllegalArgumentException("not enouht money for withdraw");
        }
        findAccount.setBalance(findAccount.getBalance().subtract(withdrawDto.amount()));
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
