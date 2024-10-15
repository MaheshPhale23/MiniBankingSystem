package com.ms.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.config.JwtHelper;
import com.ms.entities.*;
import com.ms.helper.*;
import com.ms.service.impl.UserDetailImpl;

@RestController
@CrossOrigin("*")
public class AuthenticationController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserDetailImpl userDetailImpl;
	
	@Autowired
	JwtHelper jwtHelper;
	
	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody com.ms.helper.TokenRequest tokenRequest) throws Exception {
		
		try {
			
			authenticate(tokenRequest.getUsername(), tokenRequest.getPassword());
			
		} catch (Exception e) {
			e.printStackTrace();
            throw new Exception("User not found ");
		}
		
		UserDetails userDetails = this.userDetailImpl.loadUserByUsername(tokenRequest.getUsername());
		String token = this.jwtHelper.generateToken(userDetails);
		
		
		
		return ResponseEntity.ok(new TokenResponse(token));
	}
	private void authenticate(String username, String password) throws Exception {
		
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
		} catch (DisabledException e) {
            throw new Exception("USER DISABLED " + e.getMessage());
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid Credentials " + e.getMessage());
        }
		
	}
	@GetMapping("/current-user")
	public User getCurrentUser(Principal principal) {
		return ((User) this.userDetailImpl.loadUserByUsername(principal.getName()));
	}

}
