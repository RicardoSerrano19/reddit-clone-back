package com.serrano.app.forum.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.serrano.app.forum.domain.User;
import com.serrano.app.forum.domain.VerificationToken;
import com.serrano.app.forum.dto.RegisterRequest;
import com.serrano.app.forum.dto.ServiceResponse;
import com.serrano.app.forum.exception.CustomApiException;
import com.serrano.app.forum.exception.TokenNotFoundException;
import com.serrano.app.forum.exception.UsernameNotFoundException;
import com.serrano.app.forum.repository.UserRepository;
import com.serrano.app.forum.repository.VerificationTokenRepository;
import com.serrano.app.forum.utils.RegularToken;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {
	private final ModelMapper mapper;
	private final PasswordEncoder encoder;
	private final UserRepository userRepo;
	private final VerificationTokenRepository tokenRepo;
	private final MailService mail;
	
	@Autowired
	public AuthService(ModelMapper mapper, PasswordEncoder encoder, UserRepository userRepo, VerificationTokenRepository tokenRepo, MailService mailService) {
		this.mapper = mapper;
		this.encoder = encoder;
		this.userRepo = userRepo;
		this.tokenRepo = tokenRepo;
		this.mail = mailService;
	}
	
	@Transactional
	public ServiceResponse signup(RegisterRequest request) {
		User user = mapper.map(request, User.class);
		user.setPassword(encoder.encode(request.getPassword()));
		user.setCreated_at(Instant.now());
		user.setEnabled(false);
		VerificationToken token = RegularToken.create(user, 10);
		try {
			userRepo.save(user);
			tokenRepo.save(token);
			mail.send(user.getUsername(), token.getToken());
			return new ServiceResponse("User registration succesfully", HttpStatus.CREATED);
		}catch(Exception ex) {
			throw new CustomApiException("Something went wrong when try to signup", 
					new ArrayList<String>(Arrays.asList(ex.getMessage())) );
		}
		
	}
	
	public ServiceResponse verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = tokenRepo.findByToken(token);
		verificationToken.orElseThrow(() -> new TokenNotFoundException(token));
		fetchUserAndEnable(verificationToken.get());
		verifiyToken(verificationToken.get());
		return new ServiceResponse("User verification succesfully", HttpStatus.OK);
	}
	
	@Transactional
	public void fetchUserAndEnable(VerificationToken token) {
		String username = token.getUser().getUsername();
		User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		user.setEnabled(true);
		try {
			userRepo.save(user);
			log.info("User enabled change to true");
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
	}
	
	@Transactional
	public void verifiyToken(VerificationToken token) {
		try {
			token.setConfirmed_at(Instant.now());
			tokenRepo.save(token);
			log.info("Token confirmed_at change to currentTime");
		}catch(Exception ex) {
			log.error(ex.getMessage());
		}
		
	}
}