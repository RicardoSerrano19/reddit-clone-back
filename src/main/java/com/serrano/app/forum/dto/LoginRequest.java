package com.serrano.app.forum.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	
    @NotEmpty(message = "username should not be empty")
    @Size(min = 5, max = 50, message 
      = "username must be between 5 and 50 characters")
	private String username;
    @NotEmpty(message = "password should not be empty")
    @Size(min = 5, max = 100, message 
    = "password must be between 8 and 100 characters")
	private String password;
    
}
