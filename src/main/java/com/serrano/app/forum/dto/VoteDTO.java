package com.serrano.app.forum.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.serrano.app.forum.enums.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "voteType should not be empty")
	private VoteType voteType;
	@NotNull(message = "postId should not be empty")
    @Positive(message = "postId should be valid number")
	private Long postId;

}
