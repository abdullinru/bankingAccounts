package com.github.abdullinru.bankingAccounts.dto;

import java.math.BigDecimal;

public record DepositDto(long accountId, BigDecimal amount) {

}

