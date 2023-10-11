package com.github.abdullinru.bankingAccounts.dto;

public class TransferDto {
    private long senderId;
    private long recipientId;
    private double transferAmount;
    private String pinCode;

    public long getSenderId() {
        return senderId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public String getPinCode() {
        return pinCode;
    }

}
