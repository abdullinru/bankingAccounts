package com.github.abdullinru.bankingAccounts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abdullinru.bankingAccounts.dto.*;
import com.github.abdullinru.bankingAccounts.mapper.AccountMapper;
import com.github.abdullinru.bankingAccounts.mapper.TransferMapper;
import com.github.abdullinru.bankingAccounts.model.Account;
import com.github.abdullinru.bankingAccounts.repository.AccountRepository;
import com.github.abdullinru.bankingAccounts.service.BalanceService;
import com.github.abdullinru.bankingAccounts.service.TransferService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MoneyOperationController.class)
class MoneyOperationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private BalanceService balanceService;
    @SpyBean
    private TransferService transferService;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private TransferMapper transferMapper;
    @MockBean
    private AccountMapper accountMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @InjectMocks
    private MoneyOperationController controller;

    @Test
    public void depositPositiveTest() throws Exception{

        DepositDto depositDto = new DepositDto(100L, BigDecimal.valueOf(50));
        String jsonDepositDto = objectMapper.writeValueAsString(depositDto);

        Account account = new Account(100L,"first", BigDecimal.valueOf(100.99), "1111");
        ResponseAccountDto responseAccountDto = new ResponseAccountDto("first", BigDecimal.valueOf(150.99));

        Mockito.when(accountRepository.findById(depositDto.accountId())).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountMapper.toResponseAccountDto(account)).thenReturn(responseAccountDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/deposit")
                        .content(jsonDepositDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(responseAccountDto.name()))
                .andExpect(jsonPath("$.balance").value(responseAccountDto.balance()));
    }
    @Test
    public void depositNegativeTestInCorrectAmount() throws Exception{

        DepositDto depositDto = new DepositDto(100L, BigDecimal.valueOf(-50));
        String jsonDepositDto = objectMapper.writeValueAsString(depositDto);

        Account account = new Account(100L,"first", BigDecimal.valueOf(100.99), "1111");
        ResponseAccountDto responseAccountDto = new ResponseAccountDto("first", BigDecimal.valueOf(150.99));

        Mockito.when(accountRepository.findById(depositDto.accountId())).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountMapper.toResponseAccountDto(account)).thenReturn(responseAccountDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/deposit")
                        .content(jsonDepositDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void depositNegativeTestAccountNotFound() throws Exception{

        DepositDto depositDto = new DepositDto(100L, BigDecimal.valueOf(50));
        String jsonDepositDto = objectMapper.writeValueAsString(depositDto);

        Account account = new Account(100L,"first", BigDecimal.valueOf(100.99), "1111");
        ResponseAccountDto responseAccountDto = new ResponseAccountDto("first", BigDecimal.valueOf(150.99));

        Mockito.when(accountRepository.findById(depositDto.accountId())).thenReturn(Optional.empty());
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountMapper.toResponseAccountDto(account)).thenReturn(responseAccountDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/deposit")
                        .content(jsonDepositDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    /////////////////////////////////////

    @Test
    public void withdrawPositiveTest() throws Exception{

        WithdrawDto withdrawDto = new WithdrawDto(100L, BigDecimal.valueOf(50), "1111");
        String jsonWithdrawDto = objectMapper.writeValueAsString(withdrawDto);

        Account account = new Account(100L,"first", BigDecimal.valueOf(100.99), "1111");
        ResponseAccountDto responseAccountDto = new ResponseAccountDto("first", BigDecimal.valueOf(150.99));

        Mockito.when(accountRepository.findById(withdrawDto.accountId())).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountMapper.toResponseAccountDto(account)).thenReturn(responseAccountDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/withdraw")
                        .content(jsonWithdrawDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(responseAccountDto.name()))
                .andExpect(jsonPath("$.balance").value(responseAccountDto.balance()));
    }
    @Test
    public void withdrawNegativeTestInCorrectAmount() throws Exception{

        WithdrawDto withdrawDto = new WithdrawDto(100L, BigDecimal.valueOf(-50), "1111");
        String jsonWithdrawDto = objectMapper.writeValueAsString(withdrawDto);

        Account account = new Account(100L,"first", BigDecimal.valueOf(100.99), "1111");
        ResponseAccountDto responseAccountDto = new ResponseAccountDto("first", BigDecimal.valueOf(150.99));

        Mockito.when(accountRepository.findById(withdrawDto.accountId())).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountMapper.toResponseAccountDto(account)).thenReturn(responseAccountDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/withdraw")
                        .content(jsonWithdrawDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void withdrawNegativeTestInCorrectPinCode() throws Exception{

        WithdrawDto withdrawDto = new WithdrawDto(100L, BigDecimal.valueOf(50), "incorrect");
        String jsonWithdrawDto = objectMapper.writeValueAsString(withdrawDto);

        Account account = new Account(100L,"first", BigDecimal.valueOf(100.99), "1111");
        ResponseAccountDto responseAccountDto = new ResponseAccountDto("first", BigDecimal.valueOf(150.99));

        Mockito.when(accountRepository.findById(withdrawDto.accountId())).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountMapper.toResponseAccountDto(account)).thenReturn(responseAccountDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/withdraw")
                        .content(jsonWithdrawDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void withdrawNegativeTestPinCodeNotMatch() throws Exception{

        WithdrawDto withdrawDto = new WithdrawDto(100L, BigDecimal.valueOf(50), "2222");
        String jsonWithdrawDto = objectMapper.writeValueAsString(withdrawDto);

        Account account = new Account(100L,"first", BigDecimal.valueOf(100.99), "1111");
        ResponseAccountDto responseAccountDto = new ResponseAccountDto("first", BigDecimal.valueOf(150.99));

        Mockito.when(accountRepository.findById(withdrawDto.accountId())).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountMapper.toResponseAccountDto(account)).thenReturn(responseAccountDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/withdraw")
                        .content(jsonWithdrawDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void withdrawNegativeTestMoneyNotEnough() throws Exception{

        WithdrawDto withdrawDto = new WithdrawDto(100L, BigDecimal.valueOf(10000), "1111");
        String jsonWithdrawDto = objectMapper.writeValueAsString(withdrawDto);

        Account account = new Account(100L,"first", BigDecimal.valueOf(100.99), "1111");
        ResponseAccountDto responseAccountDto = new ResponseAccountDto("first", BigDecimal.valueOf(150.99));

        Mockito.when(accountRepository.findById(withdrawDto.accountId())).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountMapper.toResponseAccountDto(account)).thenReturn(responseAccountDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/withdraw")
                        .content(jsonWithdrawDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }
    @Test
    public void withdrawNegativeTestAccountNotFound() throws Exception{

        WithdrawDto withdrawDto = new WithdrawDto(100L, BigDecimal.valueOf(50), "1111");
        String jsonWithdrawDto = objectMapper.writeValueAsString(withdrawDto);

        Account account = new Account(100L,"first", BigDecimal.valueOf(100.99), "1111");
        ResponseAccountDto responseAccountDto = new ResponseAccountDto("first", BigDecimal.valueOf(150.99));

        Mockito.when(accountRepository.findById(withdrawDto.accountId())).thenReturn(Optional.empty());
        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(accountMapper.toResponseAccountDto(account)).thenReturn(responseAccountDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/withdraw")
                        .content(jsonWithdrawDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
    @Test
    public void transferPositiveTest() throws Exception{

        TransferDto transferDto = new TransferDto(100L, 200L, BigDecimal.valueOf(50), "1111");
        DepositDto depositDto = new DepositDto(200L, BigDecimal.valueOf(50));
        WithdrawDto withdrawDto = new WithdrawDto(100L, BigDecimal.valueOf(50), "1111");
        String jsonTransferDto = objectMapper.writeValueAsString(transferDto);

        Account sender = new Account(100L,"first", BigDecimal.valueOf(100.99), "1111");
        Account recipient = new Account(200L,"second", BigDecimal.valueOf(200), "2222");
        ResponseAccountDto responseAccountDto = new ResponseAccountDto("second", BigDecimal.valueOf(250));

        Mockito.when(transferMapper.toDepositDto(transferDto)).thenReturn(depositDto);
        Mockito.when(transferMapper.toWithdrawDto(transferDto)).thenReturn(withdrawDto);
        Mockito.when(accountRepository.findById(depositDto.accountId())).thenReturn(Optional.of(recipient));
        Mockito.when(accountRepository.findById(withdrawDto.accountId())).thenReturn(Optional.of(sender));
        Mockito.when(accountRepository.save(sender)).thenReturn(sender);
        Mockito.when(accountRepository.save(recipient)).thenReturn(recipient);
        Mockito.when(accountMapper.toResponseAccountDto(recipient)).thenReturn(responseAccountDto);


        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/transfer")
                        .content(jsonTransferDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(responseAccountDto.name()))
                .andExpect(jsonPath("$.balance").value(responseAccountDto.balance()));
    }

}