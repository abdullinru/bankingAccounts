package com.github.abdullinru.bankingAccounts.service;

import com.github.abdullinru.bankingAccounts.dto.DepositDto;
import com.github.abdullinru.bankingAccounts.dto.ResponseAccountDto;
import com.github.abdullinru.bankingAccounts.dto.WithdrawDto;
import com.github.abdullinru.bankingAccounts.exception.AccountNotFoundException;
import com.github.abdullinru.bankingAccounts.mapper.AccountMapper;
import com.github.abdullinru.bankingAccounts.model.Account;
import com.github.abdullinru.bankingAccounts.repository.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BalanceServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Spy
    private AccountMapper mapper;
    @InjectMocks
    private BalanceService balanceService;

    @BeforeEach
    public void init() {

    }

    @Test
    public void depositPositiveTest() {

        long senderId = 1L;
        BigDecimal amount = BigDecimal.valueOf(10);
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        BigDecimal updateBalance = BigDecimal.valueOf(110);

        DepositDto depositDto = new DepositDto(senderId, amount);
        Account currentAccount = new Account(senderId,"first", currentBalance, "1111");
        ResponseAccountDto responseAccountDto = new ResponseAccountDto("first", updateBalance);

        Mockito.when(accountRepository.findById(depositDto.accountId())).thenReturn(Optional.of(currentAccount));
        Mockito.when(accountRepository.save(currentAccount)).thenReturn(currentAccount);
        Mockito.when(mapper.toResponseAccountDto(currentAccount)).thenReturn(responseAccountDto);

        Assertions.assertThat(balanceService.deposit(depositDto)).isEqualTo(responseAccountDto);
    }
    @Test
    public void depositNegativeTestWhenIncorrectAmount() {

        long senderId = 1L;
        BigDecimal inCorrectAmount = BigDecimal.valueOf(-10);

        DepositDto depositDto = new DepositDto(senderId, inCorrectAmount);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> balanceService.deposit(depositDto));
    }
    @Test
    public void depositNegativeTestWhenRecipientNotFound() {

        long senderId = 1L;
        BigDecimal amount = BigDecimal.valueOf(10);
        DepositDto depositDto = new DepositDto(senderId, amount);

        Mockito.when(accountRepository.findById(depositDto.accountId())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(AccountNotFoundException.class)
                .isThrownBy(() -> balanceService.deposit(depositDto));
    }
/////////////////////////
@Test
public void withdrawPositiveTest() {

    long senderId = 1L;
    BigDecimal amount = BigDecimal.valueOf(10);
    BigDecimal currentBalance = BigDecimal.valueOf(100);
    BigDecimal updateBalance = BigDecimal.valueOf(90);
    String pinCode = "1111";

    WithdrawDto withdrawDto = new WithdrawDto(senderId, amount, pinCode);
    Account currentAccount = new Account(senderId,"first", currentBalance, "1111");
    ResponseAccountDto responseAccountDto = new ResponseAccountDto("first", updateBalance);

    Mockito.when(accountRepository.findById(withdrawDto.accountId())).thenReturn(Optional.of(currentAccount));
    Mockito.when(accountRepository.save(currentAccount)).thenReturn(currentAccount);
    Mockito.when(mapper.toResponseAccountDto(currentAccount)).thenReturn(responseAccountDto);

    Assertions.assertThat(balanceService.withdraw(withdrawDto)).isEqualTo(responseAccountDto);
}
    @Test
    public void withdrawNegativeTestWhenIncorrectAmount() {

        long senderId = 1L;
        BigDecimal inCorrectAmount = BigDecimal.valueOf(-10);
        String pinCode = "1111";

        WithdrawDto withdrawDto = new WithdrawDto(senderId, inCorrectAmount, pinCode);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> balanceService.withdraw(withdrawDto));
    }
    @Test
    public void withdrawNegativeTestWhenSenderNotFound() {

        long senderId = 1L;
        BigDecimal amount = BigDecimal.valueOf(10);
        String pinCode = "1111";
        WithdrawDto withdrawDto = new WithdrawDto(senderId, amount, pinCode);

        Mockito.when(accountRepository.findById(withdrawDto.accountId())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(AccountNotFoundException.class)
                .isThrownBy(() -> balanceService.withdraw(withdrawDto));
    }
    @Test
    public void withdrawNegativeTestWhenInCorrectPincode() {

        long senderId = 1L;
        BigDecimal amount = BigDecimal.valueOf(10);
        String inCorrectPinCode = "incorrect";

        WithdrawDto withdrawDto = new WithdrawDto(senderId, amount, inCorrectPinCode);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> balanceService.withdraw(withdrawDto));
    }
    @Test
    public void withdrawNegativeTestWhenPincodeNotMatch() {

        long senderId = 1L;
        BigDecimal amount = BigDecimal.valueOf(10);
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        String pinCode = "1111";
        String wrongPinCode = "2222";

        WithdrawDto withdrawDto = new WithdrawDto(senderId, amount, wrongPinCode);
        Account currentAccount = new Account(senderId,"first", currentBalance, pinCode);

        Mockito.when(accountRepository.findById(withdrawDto.accountId())).thenReturn(Optional.of(currentAccount));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> balanceService.withdraw(withdrawDto));
    }
    @Test
    public void withdrawNegativeTestWhenMoneyNotEnough() {

        long senderId = 1L;
        BigDecimal amount = BigDecimal.valueOf(100000);
        BigDecimal currentBalance = BigDecimal.valueOf(100);
        String pinCode = "1111";

        WithdrawDto withdrawDto = new WithdrawDto(senderId, amount, pinCode);
        Account currentAccount = new Account(senderId,"first", currentBalance, pinCode);

        Mockito.when(accountRepository.findById(withdrawDto.accountId())).thenReturn(Optional.of(currentAccount));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> balanceService.withdraw(withdrawDto));
    }

}