package com.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.entities.User;

public interface UserRepo extends JpaRepository<User, Long>{
	
	User findByUsername(String username);
    User findByEmail(String email);

}
