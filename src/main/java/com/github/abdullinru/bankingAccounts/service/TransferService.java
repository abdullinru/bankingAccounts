package com.github.abdullinru.bankingAccounts.service;

import com.github.abdullinru.bankingAccounts.dto.DepositDto;
import com.github.abdullinru.bankingAccounts.dto.ResponseAccountDto;
import com.github.abdullinru.bankingAccounts.dto.TransferDto;
import com.github.abdullinru.bankingAccounts.dto.WithdrawDto;
import com.github.abdullinru.bankingAccounts.exception.AccountNotFoundException;
import com.github.abdullinru.bankingAccounts.mapper.AccountMapper;
import com.github.abdullinru.bankingAccounts.mapper.TransferMapper;
import com.github.abdullinru.bankingAccounts.model.Account;
import com.github.abdullinru.bankingAccounts.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
    private final AccountRepository accountRepository;
    private final BalanceService balanceService;
    private final TransferMapper mapper;

    public TransferService(AccountRepository accountRepository, BalanceService balanceService, TransferMapper mapper) {
        this.accountRepository = accountRepository;
        this.balanceService = balanceService;
        this.mapper = mapper;
    }

    @Transactional
    public ResponseAccountDto transfer(TransferDto transferDto) {
        WithdrawDto withdrawDto = mapper.toWithdrawDto(transferDto);
        balanceService.withdraw(withdrawDto);

        DepositDto depositDto = mapper.toDepositDto(transferDto);
        return balanceService.deposit(depositDto);

//        chechAmount(transferDto.getTransferAmount());
//        checkPin(transferDto.getPinCode());
//        Account findSender = accountRepository
//                .findById(transferDto.getSenderId())
//                .orElseThrow(AccountNotFoundException::new);
//
//        Account findRecipient = accountRepository
//                .findById(transferDto.getRecipientId())
//                .orElseThrow(AccountNotFoundException::new);
//
//        comparePinCodes(transferDto.getPinCode(), findSender.getPinCode());
//
//        if (findSender.getBalance() < transferDto.getTransferAmount()) {
//            throw new IllegalArgumentException("not enouht money for withdraw");
//        }
//        findSender.setBalance(findSender.getBalance() - transferDto.getTransferAmount());
//        findRecipient.setBalance(findRecipient.getBalance() + transferDto.getTransferAmount());
//        accountRepository.save(findRecipient);
//        accountRepository.save(findSender);
//
    }
}
