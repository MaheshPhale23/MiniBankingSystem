package com.ms.service;

import java.util.Date;
import java.util.List;

import com.ms.entities.Transaction;

public interface TransactionService {
	
	// Method to create a new transaction (deposit or withdrawal) for an account
    Transaction createTransaction(Long accountId, Transaction transaction);
    
    // Method to retrieve a transaction by its unique ID
    Transaction getTransactionById(Long transactionId);
    
    // Method to get all transactions associated with a specific account
    List<Transaction> getTransactionsByAccountId(Long accountId);
    
    // Method to get all transactions for an account within a specific date range
    List<Transaction> getTransactionsByAccountIdAndDateRange(Long accountId, Date startDate, Date endDate);
    
    // Method to retrieve all transactions for a user
    List<Transaction> getTransactionsByUserId(Long userId);

}
