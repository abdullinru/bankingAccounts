package com.github.abdullinru.bankingAccounts.controller;

import com.github.abdullinru.bankingAccounts.dto.DepositDto;
import com.github.abdullinru.bankingAccounts.dto.ResponseAccountDto;
import com.github.abdullinru.bankingAccounts.dto.TransferDto;
import com.github.abdullinru.bankingAccounts.dto.WithdrawDto;
import com.github.abdullinru.bankingAccounts.service.AccountService;
import com.github.abdullinru.bankingAccounts.service.BalanceService;
import com.github.abdullinru.bankingAccounts.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoneyOperationController {
    private final BalanceService balanceService;
    private final TransferService transferService;


    public MoneyOperationController(BalanceService balanceService, TransferService transferService) {
        this.balanceService = balanceService;
        this.transferService = transferService;
    }

    @Operation(summary = "Внести депозит на баланс аккаунта",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = ResponseAccountDto.class))),
            @ApiResponse(responseCode = "400", description = "incorrect arguments"),
            @ApiResponse(responseCode = "404", description = "not found account by id")})
    @PatchMapping("/deposit")
    public ResponseAccountDto deposit(@RequestBody DepositDto depositDto) {
        return balanceService.deposit(depositDto);
    }

    @Operation(summary = "Снять деньги с баланса аккаунта",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = ResponseAccountDto.class))),
            @ApiResponse(responseCode = "404", description = "not found account by id"),
            @ApiResponse(responseCode = "400", description = "incorrect pin  or not enough money")})
    @PatchMapping("/withdraw")
    public ResponseAccountDto withdraw(@RequestBody WithdrawDto withdrawDto) {
        return balanceService.withdraw(withdrawDto);
    }

    @Operation(summary = "Перевести деньги с баланса одного аккаунта на баланс другого аккаунта",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = ResponseAccountDto.class))),
            @ApiResponse(responseCode = "404", description = "not found account of sender or recipient"),
            @ApiResponse(responseCode = "400", description = "incorrect pin  or not enough money")})
    @PatchMapping("/transfer")
    public ResponseAccountDto transfer(@RequestBody TransferDto transferDto) {
        return transferService.transfer(transferDto);
    }
}
