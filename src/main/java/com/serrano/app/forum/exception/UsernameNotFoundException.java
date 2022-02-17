package com.serrano.app.forum.exception;

import java.util.ArrayList;
import java.util.List;

public class UsernameNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	List<String> errors = new ArrayList<>();
	
	public UsernameNotFoundException(String username){
        super("Username not found");
        this.errors.add(String.format("The username: %s is not found", username));
    }
	
	public List<String> getErrors() {
		return errors;
	}
}
