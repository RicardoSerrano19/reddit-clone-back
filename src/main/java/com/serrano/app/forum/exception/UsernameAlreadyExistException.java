package com.serrano.app.forum.exception;

import java.util.ArrayList;
import java.util.List;

public class UsernameAlreadyExistException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	List<String> errors = new ArrayList<>();
	
	public UsernameAlreadyExistException(String username){
        super("Username already exist");
        this.errors.add(String.format("The username: %s is already in use. Try another", username));
    }
	
	public List<String> getErrors() {
		return errors;
	}
}
