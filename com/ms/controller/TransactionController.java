package com.ms.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ms.entities.Transaction;
import com.ms.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin("*")
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	
	// Create a new transaction
    @PostMapping("/{accountId}")
    public ResponseEntity<Transaction> createTransaction(@PathVariable Long accountId, @RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(accountId, transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }
    
    // Get a transaction by ID
    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long transactionId) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(transaction);
    }

    // Get all transactions for a specific account
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable Long accountId) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(transactions);
    }

    // Get transactions for an account within a date range
    @GetMapping("/account/{accountId}/dateRange")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountIdAndDateRange(
            @PathVariable Long accountId, 
            @RequestParam Date startDate, 
            @RequestParam Date endDate) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountIdAndDateRange(accountId, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    // Get all transactions for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);
        return ResponseEntity.ok(transactions);
    }

}
