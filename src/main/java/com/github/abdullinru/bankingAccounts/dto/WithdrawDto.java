package com.github.abdullinru.bankingAccounts.dto;

import java.math.BigDecimal;

public class WithdrawDto {
    private long accountId;
    private BigDecimal amount;
    private String pinCode;

    public long getAccountId() {
        return accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
