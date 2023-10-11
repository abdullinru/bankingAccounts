package com.github.abdullinru.bankingAccounts.dto;

import java.math.BigDecimal;

public class DepositDto {
    private long accountId;
    private BigDecimal amount;

    public long getAccountId() {
        return accountId;
    }
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
