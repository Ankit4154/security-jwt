package com.users.securityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.users.securityjwt.AuthenticationRequest;
import com.users.securityjwt.AuthenticationResponse;
import com.users.securityjwt.service.MyUserDetails;
import com.users.securityjwt.util.JWTUtil;

@RestController
public class UserContoller {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetails userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@RequestMapping("/hello")
	public ResponseEntity<String> getHello(){
		return ResponseEntity.ok("Hello");
	}
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public ResponseEntity<?> createJWT(@RequestBody AuthenticationRequest authRequest) throws Exception{
		try {
			authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		}catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
