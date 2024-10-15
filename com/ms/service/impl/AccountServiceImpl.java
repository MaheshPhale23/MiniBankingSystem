package com.ms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ms.service.AccountService;
import com.ms.entities.Account;
import com.ms.entities.User;
import com.ms.repository.AccountRepo;
import com.ms.repository.UserRepo;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	AccountRepo accountRepo;
	
	@Autowired
	UserRepo userRepo;

	@Override
	public Account createAccount(Long userId, Account account) {
		// Fetch the user to whom this account will belong
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id " + userId));

        // Set the user in the account and save it
        account.setUser(user); // Make sure to add a setUser method in Account entity
        account.setCreatedAt(new Date());
        return accountRepo.save(account);
	}

	@Override
	public Optional<Account> getAccountById(Long accountId) {
		// Fetch account by its ID
        return accountRepo.findById(accountId);
	}

	@Override
	public List<Account> getAccountsByUserId(Long userId) {
		// Fetch all accounts associated with the user
        return accountRepo.findByUser_UserId(userId);
	}

	@Override
	public Account deposit(Long accountId, double amount) {
		Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id " + accountId));
        account.setBalance(account.getBalance() + amount); // Update the balance
        return accountRepo.save(account);
	}

	@Override
	public Account withdraw(Long accountId, double amount) {
		Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id " + accountId));
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount); // Update the balance
        return accountRepo.save(account);
	}

	@Override
	public void deleteAccount(Long accountId) {
		accountRepo.deleteById(accountId);
		
	}

	@Override
	public Account transfer(Long fromAccountId, Long toAccountId, double amount) {
		withdraw(fromAccountId, amount); // Withdraw from the sender's account
        return deposit(toAccountId, amount);
	}

	@Override
	public double getBalance(Long accountId) {
		Account account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with id " + accountId));
        return account.getBalance(); // Return the account balance
	}

}
