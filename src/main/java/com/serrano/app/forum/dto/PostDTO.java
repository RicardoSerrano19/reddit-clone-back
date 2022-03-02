package com.serrano.app.forum.dto;

import java.time.Instant;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
	private Long id;
	@NotEmpty(message = "name should not be empty")
	@Size(min = 5, max = 100, message = "name must be between 5 and 100 characters")
	private String name;
	@NotEmpty(message = "url should not be empty")
	@Size(min = 5, max = 100, message = "url must be between 5 and 100 characters")
	private String url;
	@NotEmpty(message = "description should not be empty")
	@Size(min = 5, max = 100, message = "description must be between 5 and 100 characters")
	private String description;
	@NotEmpty(message = "category should not be empty")
	@Size(min = 5, max = 100, message = "category must be between 5 and 100 characters")
	private String category;
	private Instant created_at;
}
