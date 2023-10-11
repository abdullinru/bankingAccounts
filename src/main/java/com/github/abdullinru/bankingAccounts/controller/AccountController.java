package com.github.abdullinru.bankingAccounts.controller;

import com.github.abdullinru.bankingAccounts.dto.*;
import com.github.abdullinru.bankingAccounts.service.AccountService;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @Operation(summary = "Получить все аккаунты",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = ResponseAccountDto.class))),
            @ApiResponse(responseCode = "400", description = "illegal arguments")})
    @GetMapping
    public List<ResponseAccountDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @Operation(summary = "Создать новый аккаунт",
            description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = ResponseAccountDto.class))),
            @ApiResponse(responseCode = "400", description = "illegal arguments")})
    @PostMapping
    public ResponseAccountDto createAccount(@RequestBody RequestAccountDto accountDto) {
        return accountService.createAccount(accountDto);
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
        return accountService.deposit(depositDto);
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
        return accountService.withdraw(withdrawDto);
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
        return accountService.transfer(transferDto);
    }

}
