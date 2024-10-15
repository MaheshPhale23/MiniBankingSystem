package com.ms.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms.entities.User;
import com.ms.repository.UserRepo;
import com.ms.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User createUser(User user) {
		
		if (user.getRoles() == null || user.getRoles().isEmpty()) {
	        user.setRoles(new HashSet<>(Set.of("NORMAL")));
	    }
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		user.setCreatedAt(new Date());
		// Save the new user to the database
        return userRepo.save(user);
	}

	@Override
	public Optional<User> getUserById(Long userId) {
		// Find and return the user by ID
        return userRepo.findById(userId);
	}

	@Override
	public List<User> getAllUsers() {
		// Retrieve all users from the database
        return userRepo.findAll();
	}

	@Override
	public User updateUser(Long userId, User userDetails) {
		// Retrieve the user by ID, then update the details
        Optional<User> optionalUser = userRepo.findById(userId);
        
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setFullName(userDetails.getFullName());
            user.setEmail(userDetails.getEmail());
            user.setRoles(userDetails.getRoles());
            return userRepo.save(user);
        } else {
            throw new RuntimeException("User not found with id " + userId);
        }
	}

	@Override
	public void deleteUser(Long userId) {
		// Delete the user by ID
        userRepo.deleteById(userId);
		
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		// Find the user by username
        return Optional.ofNullable(userRepo.findByUsername(username));
	}

	@Override
	public Optional<User> getUserByEmail(String email) {
		// Find the user by email
        return Optional.ofNullable(userRepo.findByEmail(email));
	}

	@Override
	public User assignRole(Long userId, String role) {
		// Retrieve the user by ID and add a role
        Optional<User> optionalUser = userRepo.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getRoles().add(role);
            return userRepo.save(user);
        } else {
            throw new RuntimeException("User not found with id " + userId);
        }
	}

}
