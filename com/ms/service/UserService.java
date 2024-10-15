package com.ms.service;

import java.util.List;
import java.util.Optional;

import com.ms.entities.User;

public interface UserService {
	
	// Method to create and save a new user
    User createUser(User user);
    
    // Method to find a user by their unique ID
    Optional<User> getUserById(Long userId);
    
    // Method to retrieve all users
    List<User> getAllUsers();
    
    // Method to update an existing user's details
    User updateUser(Long userId, User userDetails);
    
    // Method to delete a user by their unique ID
    void deleteUser(Long userId);
    
    // Method to find a user by their username
    Optional<User> getUserByUsername(String username);
    
    // Method to find a user by their email
    Optional<User> getUserByEmail(String email);
    
    // Method to assign a role to a user
    User assignRole(Long userId, String role);

}
