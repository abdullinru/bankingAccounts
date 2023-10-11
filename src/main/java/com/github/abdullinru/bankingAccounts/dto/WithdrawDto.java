package com.github.abdullinru.bankingAccounts.dto;

public class WithdrawDto {
    private long accountId;
    private double amount;
    private String pinCode;

    public long getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
