package com.github.abdullinru.bankingAccounts.mapper;

import com.github.abdullinru.bankingAccounts.dto.DepositDto;
import com.github.abdullinru.bankingAccounts.dto.TransferDto;
import com.github.abdullinru.bankingAccounts.dto.WithdrawDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransferMapper {

    @Mapping(source = "senderId", target = "accountId")
    @Mapping(source = "transferAmount", target = "amount")
    @Mapping(source = "pinCode", target = "pinCode")
    WithdrawDto toWithdrawDto(TransferDto transferDto);

    @Mapping(source = "recipientId", target = "accountId")
    @Mapping(source = "transferAmount", target = "amount")
    DepositDto toDepositDto(TransferDto transferDto);
}
