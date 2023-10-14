package com.github.abdullinru.bankingAccounts.dto;

import java.math.BigDecimal;

public record ResponseAccountDto (String name, BigDecimal balance){
    }
