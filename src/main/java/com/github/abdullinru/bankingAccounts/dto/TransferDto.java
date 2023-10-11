package com.github.abdullinru.bankingAccounts.dto;

import java.math.BigDecimal;

public class TransferDto {
    private long senderId;
    private long recipientId;
    private BigDecimal transferAmount;
    private String pinCode;

    public long getSenderId() {
        return senderId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public String getPinCode() {
        return pinCode;
    }

}
