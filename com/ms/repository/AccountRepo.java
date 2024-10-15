package com.ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.entities.Account;

public interface AccountRepo extends JpaRepository<Account, Long>{
	
	Account findByAccountNumber(String accountNumber);
	
	List<Account> findByUser_UserId(Long userId);

}
