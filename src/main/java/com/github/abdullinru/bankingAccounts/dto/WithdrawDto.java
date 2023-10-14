package com.github.abdullinru.bankingAccounts.dto;

import java.math.BigDecimal;

public record WithdrawDto (long accountId, BigDecimal amount, String pinCode){

}
