package com.github.abdullinru.bankingAccounts.controller;

import com.github.abdullinru.bankingAccounts.dto.*;
import com.github.abdullinru.bankingAccounts.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseAccountDto> createAccount(@RequestBody RequestAccountDto accountDto) {
        ResponseAccountDto result = accountService.createAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
