package com.serrano.app.forum.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comments implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<CommentDTO> comments;

}
