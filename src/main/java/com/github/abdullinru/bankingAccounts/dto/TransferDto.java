package com.github.abdullinru.bankingAccounts.dto;

public class TransferDto {
    private long senderId;
    private long recipientId;
    private double transferAmount;
    private int pinCode;

    public long getSenderId() {
        return senderId;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public int getPinCode() {
        return pinCode;
    }
}
