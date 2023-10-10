package com.github.abdullinru.bankingAccounts.dto;

public class RequestAccountDto {
    private String name;
    private double balance;
    private String pinCode;

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public String getPinCode() {
        return pinCode;
    }
}
