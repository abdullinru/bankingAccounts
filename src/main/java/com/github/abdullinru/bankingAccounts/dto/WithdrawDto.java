package com.github.abdullinru.bankingAccounts.dto;

public class WithdrawDto {
    private long accountId;
    private double amount;
    private int pinCode;

    public long getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public int getPinCode() {
        return pinCode;
    }
}
