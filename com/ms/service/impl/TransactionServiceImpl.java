package com.ms.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.entities.Account;
import com.ms.entities.Transaction;
import com.ms.repository.AccountRepo;
import com.ms.repository.TransactionRepo;
import com.ms.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	AccountRepo accountRepo;
	
	@Autowired
	TransactionRepo transactionRepo;

	@Override
	public Transaction createTransaction(Long accountId, Transaction transaction) {
		Account account = accountRepo.findById(accountId)
	            .orElseThrow(() -> new RuntimeException("Account not found with id " + accountId));
	    
	    // Check the transaction type and adjust the account balance
	    if ("DEPOSIT".equalsIgnoreCase(transaction.getType())) {
	        account.setBalance(account.getBalance() + transaction.getAmount());
	    } else if ("WITHDRAWAL".equalsIgnoreCase(transaction.getType())) {
	        if (account.getBalance() < transaction.getAmount()) {
	            throw new RuntimeException("Insufficient funds");
	        }
	        account.setBalance(account.getBalance() - transaction.getAmount());
	    } else {
	        throw new RuntimeException("Invalid transaction type");
	    }
	    
	    // Save the updated account balance
	    accountRepo.save(account);

	    // Associate the transaction with the account and set the transaction date
	    transaction.setAccount(account);
	    transaction.setTransactionDate(new Date());

	    // Save the transaction and return it
	    return transactionRepo.save(transaction);
	}

	@Override
	public Transaction getTransactionById(Long transactionId) {
		// Find and return the transaction by ID
        return transactionRepo.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id " + transactionId));
	}

	@Override
	public List<Transaction> getTransactionsByAccountId(Long accountId) {
		List<Transaction> transactions = transactionRepo.findByAccount_AccountId(accountId);
	    System.out.println("Transactions found for account ID " + accountId + ": " + transactions.size());
	    return transactions;
	}

	@Override
	public List<Transaction> getTransactionsByAccountIdAndDateRange(Long accountId, Date startDate, Date endDate) {
		// Retrieve transactions for an account within a specific date range
        return transactionRepo.findByAccount_AccountIdAndTransactionDateBetween(accountId, startDate, endDate);
	}

	@Override
	public List<Transaction> getTransactionsByUserId(Long userId) {
		// Retrieve transactions for all accounts belonging to a user
        return transactionRepo.findByAccount_User_UserId(userId);
	}

}
