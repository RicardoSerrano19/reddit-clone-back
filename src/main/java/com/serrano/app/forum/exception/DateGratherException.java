package com.serrano.app.forum.exception;

import java.util.ArrayList;
import java.util.List;

public class DateGratherException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	List<String> errors = new ArrayList<>();
	
	public DateGratherException(String provide, String current){
        super("Provide date is grather than current date");
        this.errors.add(String.format("The date: %s is grather than %s", provide, current));
    }
	
	public List<String> getErrors() {
		return errors;
	}
}
