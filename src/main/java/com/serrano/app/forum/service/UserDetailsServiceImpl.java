package com.serrano.app.forum.service;

import java.util.Collection;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.serrano.app.forum.domain.User;
import com.serrano.app.forum.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepo;
	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepo) {
		this.userRepo = userRepo;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByUsername(username);
		if(user.isEmpty()) throw new UsernameNotFoundException("Username not found");
		log.info("UserDetailsService - User {}", user.get().toString());
        return new org.springframework.security.core.userdetails.User(
        		user.get().getUsername(), 
        		user.get().getPassword(),
        		user.get().getEnabled(),
        		true,
        		true,
        		true,
        		getAuthorities(user.get()));
	}
	
	private Collection<SimpleGrantedAuthority> getAuthorities(User user){
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.getName().toString()));
	    });
		
		return authorities;
	}
}
