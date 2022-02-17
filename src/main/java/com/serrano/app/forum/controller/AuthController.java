package com.serrano.app.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serrano.app.forum.dto.RegisterRequest;
import com.serrano.app.forum.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final AuthService authService;
	@Autowired
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/signup")
	public ResponseEntity<Object> signup(@Valid @RequestBody RegisterRequest registerRequest) {
		return new ResponseEntity<Object>(authService.signup(registerRequest), HttpStatus.CREATED);
	}
	
	@GetMapping("/verification/{token}")
	public ResponseEntity<Object> verifyAccount(@PathVariable String token) {
		return new ResponseEntity<Object>(authService.verifyAccount(token), HttpStatus.OK);
	}
}
