package com.github.abdullinru.bankingAccounts.dto;

public class RequestAccountDto {
    private String name;
    private double balance;
    private int pinCode;

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public int getPinCode() {
        return pinCode;
    }
}
