package com.serrano.app.forum.exception;

import java.util.ArrayList;
import java.util.List;

public class VoteNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	List<String> errors = new ArrayList<>();
	
	public VoteNotFoundException(String vote){
        super("Vote not found");
        this.errors.add(String.format("The vote type: %s is not found", vote));
    }
	
	public VoteNotFoundException(Integer vote){
        super("Vote not found");
        this.errors.add(String.format("The vote type: %d is not found", vote));
    }
	
	public List<String> getErrors() {
		return errors;
	}
}
