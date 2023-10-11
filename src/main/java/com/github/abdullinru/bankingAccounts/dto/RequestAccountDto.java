package com.github.abdullinru.bankingAccounts.dto;

import java.math.BigDecimal;

public class RequestAccountDto {
    private String name;
    private BigDecimal balance;
    private String pinCode;

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getPinCode() {
        return pinCode;
    }
}
