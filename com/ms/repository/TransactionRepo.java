package com.ms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.entities.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Long>{
	
	List<Transaction> findByAccount_AccountId(Long accountId);
    
    List<Transaction> findByAccount_AccountIdAndTransactionDateBetween(Long accountId, Date startDate, Date endDate);
    
    List<Transaction> findByAccount_User_UserId(Long userId);

}
