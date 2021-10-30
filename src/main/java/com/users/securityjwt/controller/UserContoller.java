package com.users.securityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.users.securityjwt.service.MyUserDetails;

@RestController
public class UserContoller {
	
	@GetMapping("/hello")
	public ResponseEntity<String> getHello(){
		return ResponseEntity.ok("Hello");
	}
	/*
	@RequestMapping(value = "/auth")
	public ResponseEntity<String> createJWT(@RequestBody User user){
		String jwt = "";
		userDetails.loadUserByUsername(jwt)
		return ResponseEntity.ok(jwt);
	}*/
}
