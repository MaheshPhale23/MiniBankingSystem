package com.ms.service;

import java.util.List;
import java.util.Optional;

import com.ms.entities.Account;

public interface AccountService {
	
	// Method to create a new account for a user
    Account createAccount(Long userId, Account account);
    
    // Method to find an account by its unique ID
    Optional<Account> getAccountById(Long accountId);
    
    // Method to retrieve all accounts for a specific user
    List<Account> getAccountsByUserId(Long userId);
    
    // Method to deposit a specified amount into an account
    Account deposit(Long accountId, double amount);
    
    // Method to withdraw a specified amount from an account
    Account withdraw(Long accountId, double amount);
    
    // Method to delete an account by its unique ID
    void deleteAccount(Long accountId);
    
    // Method to transfer funds from one account to another
    Account transfer(Long fromAccountId, Long toAccountId, double amount);
    
    // Method to get the balance of a specific account
    double getBalance(Long accountId);

}
