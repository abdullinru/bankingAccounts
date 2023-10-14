package com.github.abdullinru.bankingAccounts.service;

import com.github.abdullinru.bankingAccounts.dto.RequestAccountDto;
import com.github.abdullinru.bankingAccounts.dto.ResponseAccountDto;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository repositoryMock;

    @Spy
    private AccountMapper mapperMock;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp(){

    }
    @Test
    public void getAllAccountsPositiveTest() {
        Account account1 = new Account(100L,"first", BigDecimal.valueOf(100.99), "1111");
        Account account2 = new Account(200L, "second", BigDecimal.valueOf(175.88), "2222");
        Account account3 = new Account(300L, "third", BigDecimal.valueOf(444.44), "3333");

        ResponseAccountDto accountDto1 = new ResponseAccountDto("first", BigDecimal.valueOf(100.99));
        ResponseAccountDto accountDto2 = new ResponseAccountDto("second", BigDecimal.valueOf(175.88));
        ResponseAccountDto accountDto3 = new ResponseAccountDto("third", BigDecimal.valueOf(444.44));

        List<ResponseAccountDto> accountsDto = new ArrayList<>(List.of(accountDto1, accountDto2, accountDto3));
        List<Account> accounts = new ArrayList<>(List.of(account1, account2, account3));
        Mockito.when(repositoryMock.findAll()).thenReturn(accounts);
        Mockito.when(mapperMock.toListResponseAccountDto(accounts)).thenReturn(accountsDto);

        Assertions.assertThat(accountService.getAllAccounts()).isEqualTo(accountsDto);
    }

    @Test
    public void createAccountPositiveTest() {
        long id = 1;
        String name = "test";
        BigDecimal balance = BigDecimal.valueOf(100);
        String pinCode = "5555";
        Account account = new Account(id,name, balance, pinCode);
        RequestAccountDto requestAccountDto = new RequestAccountDto(name, balance, pinCode);
        ResponseAccountDto responseAccountDto = new ResponseAccountDto(name, balance);

        Mockito.when(mapperMock.toAccount(requestAccountDto)).thenReturn(account);
        Mockito.when(repositoryMock.save(account)).thenReturn(account);
        Mockito.when(mapperMock.toResponseAccountDto(account)).thenReturn(responseAccountDto);

        Assertions.assertThat(accountService.createAccount(requestAccountDto)).isEqualTo(responseAccountDto);
    }

    @Test
    public void createAccountNegativeTestWhenIncorrectBalance() {

        String name = "test";
        BigDecimal inCorrectBalance = BigDecimal.valueOf(-100);
        String pinCode = "5555";

        RequestAccountDto requestAccountDto = new RequestAccountDto(name, inCorrectBalance, pinCode);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> accountService.createAccount(requestAccountDto));
    }
    @Test
    public void createAccountNegativeTestWhenIncorrectPinCode() {
        String name = "test";
        BigDecimal balance = BigDecimal.valueOf(100);
        String inCorrectPinCode = "aaaaaa";

        RequestAccountDto requestAccountDto = new RequestAccountDto(name, balance, inCorrectPinCode);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> accountService.createAccount(requestAccountDto));
    }

}