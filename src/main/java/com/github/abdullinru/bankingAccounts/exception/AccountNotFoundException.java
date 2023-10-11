package com.github.abdullinru.bankingAccounts.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String accountIsNotFound) {
        super(accountIsNotFound);
    }
}
