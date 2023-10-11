package com.github.abdullinru.bankingAccounts.mapper;

import com.github.abdullinru.bankingAccounts.dto.*;
import com.github.abdullinru.bankingAccounts.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "balance", target = "balance")
    @Mapping(source = "pinCode", target = "pinCode")
    Account toAccount(RequestAccountDto requestAccountDto) ;

    @Mapping(source = "name", target = "name")
    @Mapping(source = "balance", target = "balance")
    ResponseAccountDto toResponseAccountDto(Account account) ;

    List<ResponseAccountDto> toListResponseAccountDto(List<Account> accounts);
}

