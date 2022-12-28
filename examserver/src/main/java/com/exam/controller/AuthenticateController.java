package com.exam.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.config.JwtUtils;
import com.exam.entity.model.JwtRequest;
import com.exam.entity.model.JwtResponse;
import com.exam.entity.model.User;
import com.exam.helper.UserNotFoundException;

@RestController
@CrossOrigin("*")
public class AuthenticateController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtUtils jwtUtils;
	
	//generate Token API
	@PostMapping("/generatr-token")
	public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
		try {
			authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
		} catch (UserNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception("User not found");
		}
		
		//authenticate
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
		String token = this.jwtUtils.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			
		} catch (DisabledException e) {
			// TODO: handle exception
			throw new Exception("USER DISABLED" + e.getMessage());
		} catch (BadCredentialsException e) {
			// TODO: handle exception
			throw new Exception("INVALID CREDENTIALS" + e.getMessage());
		}
	}
	
	@GetMapping("/current-user")
	public User getCurrentUser(Principal principal) {
		return ((User)this.userDetailsService.loadUserByUsername(principal.getName()));
	}
}
