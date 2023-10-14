package com.github.abdullinru.bankingAccounts.dto;

import java.math.BigDecimal;

public record RequestAccountDto (String name, BigDecimal balance, String pinCode){

}
