package com.github.abdullinru.bankingAccounts.dto;

public class DepositDto {
    private long accountId;
    private double amount;

    public long getAccountId() {
        return accountId;
    }
    public double getAmount() {
        return amount;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
