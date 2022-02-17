package com.serrano.app.forum.exception;

import java.util.ArrayList;
import java.util.List;

public class TokenNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	List<String> errors = new ArrayList<>();
	
	public TokenNotFoundException(String token){
        super("Token not found");
        this.errors.add(String.format("The token: %s is not found", token));
    }
	
	public List<String> getErrors() {
		return errors;
	}
}
