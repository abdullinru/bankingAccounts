package com.github.abdullinru.bankingAccounts.dto;

import java.math.BigDecimal;

public record TransferDto (long senderId, long recipientId, BigDecimal transferAmount, String pinCode){


}
