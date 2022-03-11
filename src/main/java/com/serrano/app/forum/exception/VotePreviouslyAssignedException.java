package com.serrano.app.forum.exception;

import java.util.ArrayList;
import java.util.List;

public class VotePreviouslyAssignedException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	List<String> errors = new ArrayList<>();
	
	public VotePreviouslyAssignedException(String vote){
        super("Vote previously assigned");
        this.errors.add(String.format("The vote type: %s was assigned before", vote));
    }
	
	public VotePreviouslyAssignedException(Integer vote){
        super("Vote not found");
        this.errors.add(String.format("The vote type: %s was assigned before", vote));
    }
	
	public List<String> getErrors() {
		return errors;
	}
}
