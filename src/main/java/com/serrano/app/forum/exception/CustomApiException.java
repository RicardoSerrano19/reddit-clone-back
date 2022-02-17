package com.serrano.app.forum.exception;

import java.util.ArrayList;
import java.util.List;

public class CustomApiException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	List<String> errors = new ArrayList<>();
	
	public CustomApiException(String message, ArrayList<String> errors){
        super(message);
        this.errors = errors;
    }
	
	public List<String> getErrors() {
		return errors;
	}
}
