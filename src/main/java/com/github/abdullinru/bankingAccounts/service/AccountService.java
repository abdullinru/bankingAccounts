package com.github.abdullinru.bankingAccounts.service;

import com.github.abdullinru.bankingAccounts.dto.*;
import com.github.abdullinru.bankingAccounts.exception.AccountNotFoundException;
import com.github.abdullinru.bankingAccounts.mapper.AccountMapper;
import com.github.abdullinru.bankingAccounts.model.Account;
import com.github.abdullinru.bankingAccounts.repository.AccountRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        checkBalance(accountDto.getBalance());
        checkPin(accountDto.getPinCode());
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

    private void checkBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance is less then 0");
        }
    }

    public ResponseAccountDto deposit(DepositDto depositDto) {
        chechAmount(depositDto.getAmount());
        Account findAccount = accountRepository
                .findById(depositDto.getAccountId())
                .orElseThrow(AccountNotFoundException::new);
        findAccount.setBalance(findAccount.getBalance() + depositDto.getAmount());
        accountRepository.save(findAccount);
        ResponseAccountDto response = mapper.toResponseAccountDto(findAccount);
        return response;
    }

    private void chechAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("incorrect value of deposit");
        }
    }

    public ResponseAccountDto withdraw(WithdrawDto withdrawDto) {
        chechAmount(withdrawDto.getAmount());
        checkPin(withdrawDto.getPinCode());
        Account findAccount = accountRepository
                .findById(withdrawDto.getAccountId())
                .orElseThrow(AccountNotFoundException::new);

        comparePinCodes(withdrawDto.getPinCode(), findAccount.getPinCode());
        if (findAccount.getBalance() < withdrawDto.getAmount()) {
            throw new IllegalArgumentException("not enouht money for withdraw");
        }
        findAccount.setBalance(findAccount.getBalance() - withdrawDto.getAmount());
        accountRepository.save(findAccount);
        ResponseAccountDto response = mapper.toResponseAccountDto(findAccount);
        return response;
    }

    private void comparePinCodes(String pin1, String pin2) {
        if (!pin1.equals(pin2)) {
            throw new IllegalArgumentException("PIN codes do not match");
        }
    }

    public ResponseAccountDto transfer(TransferDto transferDto) {

        chechAmount(transferDto.getTransferAmount());
        checkPin(transferDto.getPinCode());
        Account findSender = accountRepository
                .findById(transferDto.getSenderId())
                .orElseThrow(AccountNotFoundException::new);

        Account findRecipient = accountRepository
                .findById(transferDto.getRecipientId())
                .orElseThrow(AccountNotFoundException::new);

        comparePinCodes(transferDto.getPinCode(), findSender.getPinCode());

        if (findSender.getBalance() < transferDto.getTransferAmount()) {
            throw new IllegalArgumentException("not enouht money for withdraw");
        }
        findSender.setBalance(findSender.getBalance() - transferDto.getTransferAmount());
        findRecipient.setBalance(findRecipient.getBalance() + transferDto.getTransferAmount());
        accountRepository.save(findRecipient);
        accountRepository.save(findSender);
        ResponseAccountDto response = mapper.toResponseAccountDto(findSender);
        return response;
    }
}
