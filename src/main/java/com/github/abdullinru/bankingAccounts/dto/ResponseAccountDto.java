package com.github.abdullinru.bankingAccounts.dto;

import java.math.BigDecimal;

public class ResponseAccountDto {
    private String name;
    private BigDecimal balance;

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
