package com.serrano.app.forum.mapper;

import java.time.Instant;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.serrano.app.forum.domain.Comment;
import com.serrano.app.forum.domain.Post;
import com.serrano.app.forum.domain.User;
import com.serrano.app.forum.dto.CommentDTO;

@Mapper(componentModel = "spring")
public interface CommentMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "crated_at", expression = "java(now())" )
	@Mapping(target = "post", source = "post" )
	@Mapping(target = "user", source = "user" )
	Comment mapToEntity(CommentDTO commentDTO, Post post, User user);
	
	default Instant now() {
		return Instant.now();
	}
	
	@InheritInverseConfiguration
	@Mapping(target = "username", source = "comment.user.username")
	@Mapping(target = "postId", source = "comment.post.id")
	CommentDTO mapToDTO(Comment comment);
}
