package com.serrano.app.forum.exception;

import java.util.ArrayList;
import java.util.List;

public class CategoryNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	List<String> errors = new ArrayList<>();
	
	public CategoryNotFoundException(Long id){
        super("Category not found");
        this.errors.add(String.format("Category: %s is not found", id));
    }
	
	public List<String> getErrors() {
		return errors;
	}
}
