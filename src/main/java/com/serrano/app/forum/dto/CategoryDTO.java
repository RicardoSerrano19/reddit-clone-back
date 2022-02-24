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
public class CategoryDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
    @NotEmpty(message = "name should not be empty")
    @Size(min = 5, max = 100, message 
      = "name must be between 5 and 100 characters")
	private String name;
  
    @NotEmpty(message = "description should not be empty")
    @Size(min = 5, max = 200, message 
      = "description must be between 5 and 200 characters")
	private String description;
    
    private Integer numberOfPosts;
}
