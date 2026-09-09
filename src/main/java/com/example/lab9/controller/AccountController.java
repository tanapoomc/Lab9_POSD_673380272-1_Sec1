package com.example.lab9.controller;

import com.example.lab9.model.Account;
import com.example.lab9.service.AccountService;
import com.example.lab9.service.DepositService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final DepositService depositService;

    public AccountController(AccountService accountService, DepositService depositService) {
        this.accountService = accountService;
        this.depositService = depositService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        return ResponseEntity.ok(account);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Map<String, String>> deposit(
            @PathVariable Long id,
            @RequestBody DepositRequest request) {
        depositService.deposit(id, request.getAmount());
        return ResponseEntity.ok(Collections.singletonMap("message", "Deposit successful"));
    }

    public static class DepositRequest {
        private Double amount;

        public DepositRequest() {
        }

        public DepositRequest(Double amount) {
            this.amount = amount;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }
}
