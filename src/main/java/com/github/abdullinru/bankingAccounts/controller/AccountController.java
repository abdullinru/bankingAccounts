package com.github.abdullinru.bankingAccounts.controller;

import com.github.abdullinru.bankingAccounts.dto.*;
import com.github.abdullinru.bankingAccounts.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<ResponseAccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }
    @PostMapping
    public ResponseAccountDto createAccount(@RequestBody RequestAccountDto accountDto) {
        return accountService.createAccount(accountDto);
    }
    @PatchMapping
    public ResponseAccountDto deposit(@RequestBody DepositDto depositDto) {
        return accountService.deposit(depositDto);
    }
    @PatchMapping
    public ResponseAccountDto withdraw(@RequestBody WithdrawDto withdrawDto) {
        return accountService.withdraw(withdrawDto);
    }
    @PatchMapping
    public ResponseAccountDto transfer(@RequestBody TransferDto transferDto) {
        return accountService.transfer(transferDto);
    }

}
