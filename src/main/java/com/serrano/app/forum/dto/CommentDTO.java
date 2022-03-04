package com.serrano.app.forum.dto;

import java.io.Serializable;
import java.time.Instant;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	@NotEmpty(message = "content should not be empty")
    @Size(min = 5, max = 200, message 
      = "content must be between 5 and 200 characters")
	private String content;
	private Instant crated_at;
	@NotNull(message = "postId should not be empty")
    @Positive(message = "postId should be valid number")
	private Long postId;
	private String username;

}
