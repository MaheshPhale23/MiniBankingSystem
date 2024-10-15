package com.ms.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms.entities.Account;
import com.ms.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin("*")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	// Create a new account for a user
    @PostMapping("/{userId}")
    public ResponseEntity<Account> createAccount(@PathVariable Long userId, @RequestBody Account account) {
        Account createdAccount = accountService.createAccount(userId, account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }
    
    // Get an account by ID
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) {
        Optional<Account> account = accountService.getAccountById(accountId);
        return account.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Get all accounts for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Account>> getAccountsByUserId(@PathVariable Long userId) {
        List<Account> accounts = accountService.getAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }

    // Deposit money into an account
    @PutMapping("/{accountId}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long accountId, @RequestParam double amount) {
        Account updatedAccount = accountService.deposit(accountId, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    // Withdraw money from an account
    @PutMapping("/{accountId}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable Long accountId, @RequestParam double amount) {
        Account updatedAccount = accountService.withdraw(accountId, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    // Delete an account
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }

    // Transfer money between accounts
    @PutMapping("/{fromAccountId}/transfer/{toAccountId}")
    public ResponseEntity<Account> transfer(@PathVariable Long fromAccountId, @PathVariable Long toAccountId, @RequestParam double amount) {
        Account updatedAccount = accountService.transfer(fromAccountId, toAccountId, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    // Get the balance of an account
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable Long accountId) {
        double balance = accountService.getBalance(accountId);
        return ResponseEntity.ok(balance);
    }

}
