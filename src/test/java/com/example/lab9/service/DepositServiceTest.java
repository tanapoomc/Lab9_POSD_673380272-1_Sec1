package com.example.lab9.service;

import com.example.lab9.model.Account;
import com.example.lab9.model.DepositTransaction;
import com.example.lab9.repository.AccountRepository;
import com.example.lab9.repository.DepositRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepositServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private DepositRepository depositRepository;

    private DepositService depositService;

    @BeforeEach
    void setUp() {
        depositService = new DepositService(accountRepository, depositRepository);
    }

    @Test
    void testDepositSuccess() {
        Account account = new Account("1234567890", "Test User", 0.0);
        account.setId(1L);

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        depositService.deposit(1L, 1000.0);

        assertEquals(1000.0, account.getBalance());
        verify(accountRepository).save(account);

        ArgumentCaptor<DepositTransaction> captor = ArgumentCaptor.forClass(DepositTransaction.class);
        verify(depositRepository).save(captor.capture());

        DepositTransaction savedDeposit = captor.getValue();
        assertNotNull(savedDeposit);
        assertEquals(1000.0, savedDeposit.getAmount());
        assertEquals(account, savedDeposit.getAccount());
    }
}
