package com.github.abdullinru.bankingAccounts.service;

import com.github.abdullinru.bankingAccounts.dto.DepositDto;
import com.github.abdullinru.bankingAccounts.dto.ResponseAccountDto;
import com.github.abdullinru.bankingAccounts.dto.TransferDto;
import com.github.abdullinru.bankingAccounts.dto.WithdrawDto;
import com.github.abdullinru.bankingAccounts.mapper.TransferMapper;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {
    private final BalanceService balanceService;
    private final TransferMapper mapper;

    public TransferService(BalanceService balanceService, TransferMapper mapper) {
        this.balanceService = balanceService;
        this.mapper = mapper;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Lock(LockModeType.PESSIMISTIC_READ)
    public ResponseAccountDto transfer(TransferDto transferDto) {
        WithdrawDto withdrawDto = mapper.toWithdrawDto(transferDto);
        balanceService.withdraw(withdrawDto);

        DepositDto depositDto = mapper.toDepositDto(transferDto);
        return balanceService.deposit(depositDto);

    }
}
