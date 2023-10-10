package com.github.abdullinru.bankingAccounts.repository;

import com.github.abdullinru.bankingAccounts.dto.ResponseAccountDto;
import com.github.abdullinru.bankingAccounts.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    //List<Account> findAll(Pageable pageable);
}
