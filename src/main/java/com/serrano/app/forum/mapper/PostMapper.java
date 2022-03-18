package com.serrano.app.forum.mapper;

import java.time.Instant;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.serrano.app.forum.domain.Category;
import com.serrano.app.forum.domain.Post;
import com.serrano.app.forum.domain.User;
import com.serrano.app.forum.dto.PostDTO;
import com.serrano.app.forum.repository.CommentRepository;
import com.serrano.app.forum.utils.TimeAgo;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
	
	@Autowired
	private CommentRepository commentRepo;
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "name", source = "postDTO.name")
	@Mapping(target = "url", source = "postDTO.url")
	@Mapping(target = "description", source = "postDTO.description")
	@Mapping(target = "votes", ignore = true)
	@Mapping(target = "category", source = "category")
	@Mapping(target = "user", source = "user")
	@Mapping(target = "created_at", expression = "java(now())")
	public abstract Post mapToEntity(PostDTO postDTO, Category category, User user);
	
	Instant now() {
		return Instant.now();
	}
	
	
	@InheritInverseConfiguration
	@Mapping(target = "category", source = "post.category.name")
	@Mapping(target = "commentCount", expression =  "java(commentCount(post))")
	@Mapping(target = "voteCount", source = "post.votes")
	@Mapping(target = "duration", expression = "java(timeAgo(post.getCreated_at()))")
	public abstract PostDTO mapToDTO(Post post);
	
	String timeAgo(Instant instant) {
		return TimeAgo.calculateTimeAgo(instant);
	}
	
	Integer commentCount(Post post) {
		return commentRepo.countByPost(post).intValue();
	}
}
