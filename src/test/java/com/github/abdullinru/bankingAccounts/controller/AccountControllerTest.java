package com.github.abdullinru.bankingAccounts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abdullinru.bankingAccounts.dto.RequestAccountDto;
import com.github.abdullinru.bankingAccounts.dto.ResponseAccountDto;
import com.github.abdullinru.bankingAccounts.mapper.AccountMapper;
import com.github.abdullinru.bankingAccounts.model.Account;
import com.github.abdullinru.bankingAccounts.repository.AccountRepository;
import com.github.abdullinru.bankingAccounts.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
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
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(controllers = AccountController.class)
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private AccountService accountService;
    @MockBean
    private AccountMapper mapper;
    @MockBean
    private AccountRepository accountRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setUp() {

    }
    @Test
    public void getAllAccountsPositiveTest() throws Exception{

        Account account1 = new Account(100L,"first", BigDecimal.valueOf(100.99), "1111");
        Account account2 = new Account(200L, "second", BigDecimal.valueOf(175.88), "2222");
        Account account3 = new Account(300L, "third", BigDecimal.valueOf(444.44), "3333");

        ResponseAccountDto accountDto1 = new ResponseAccountDto("first", BigDecimal.valueOf(100.99));
        ResponseAccountDto accountDto2 = new ResponseAccountDto("second", BigDecimal.valueOf(175.88));
        ResponseAccountDto accountDto3 = new ResponseAccountDto("third", BigDecimal.valueOf(444.44));

        List<ResponseAccountDto> accountsDto = new ArrayList<>(List.of(accountDto1, accountDto2, accountDto3));
        List<Account> accounts = new ArrayList<>(List.of(account1, account2, account3));
        Mockito.when(accountRepository.findAll()).thenReturn(accounts);
        Mockito.when(mapper.toListResponseAccountDto(accounts)).thenReturn(accountsDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/account")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(accountDto1.name()))
                .andExpect(jsonPath("$[0].balance").value(accountDto1.balance()))
                .andExpect(jsonPath("$[1].name").value(accountDto2.name()))
                .andExpect(jsonPath("$[1].balance").value(accountDto2.balance()))
                .andExpect(jsonPath("$[2].name").value(accountDto3.name()))
                .andExpect(jsonPath("$[2].balance").value(accountDto3.balance()));
    }
    @Test
    public void createAccountPositiveTest() throws Exception{

        RequestAccountDto requestAccountDto = new RequestAccountDto("first", BigDecimal.valueOf(100.99),"1111" );
        ResponseAccountDto responseAccountDto = new ResponseAccountDto("first", BigDecimal.valueOf(100.99));
        Account account = new Account(100L,"first", BigDecimal.valueOf(100.99), "1111");
        String jsonAccountDto = objectMapper.writeValueAsString(requestAccountDto);

        Mockito.when(accountRepository.save(account)).thenReturn(account);
        Mockito.when(mapper.toAccount(requestAccountDto)).thenReturn(account);
        Mockito.when(mapper.toResponseAccountDto(account)).thenReturn(responseAccountDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/account")
                        .content(jsonAccountDto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(responseAccountDto.name()))
                .andExpect(jsonPath("$.balance").value(responseAccountDto.balance()));
    }

}