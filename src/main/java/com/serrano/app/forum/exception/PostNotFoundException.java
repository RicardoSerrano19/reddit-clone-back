package com.serrano.app.forum.exception;

import java.util.ArrayList;
import java.util.List;

public class PostNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	List<String> errors = new ArrayList<>();
	
	public PostNotFoundException(String post){
        super("Post not found");
        this.errors.add(String.format("The post: %s is not found", post));
    }
	
	public PostNotFoundException(Long id){
        super("Post not found");
        this.errors.add(String.format("The post: %s is not found", id));
    }
	
	public List<String> getErrors() {
		return errors;
	}
}
