package com.serrano.app.forum.mapper;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.serrano.app.forum.domain.Category;
import com.serrano.app.forum.domain.Post;
import com.serrano.app.forum.domain.User;
import com.serrano.app.forum.dto.CategoryDTO;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

	@Mapping(target = "numberOfPosts", expression =  "java(getNumberOfPost(category.getPosts()))")
	CategoryDTO mapToDTO(Category category);
	
	default Integer getNumberOfPost(Set<Post> numberOfPosts) {
		return numberOfPosts.size();
	}
	
	@InheritInverseConfiguration
	@Mapping(target = "posts", expression = "java(generatePosts())")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "created_at", expression = "java(now())")
	@Mapping(target = "user", source = "user")
	Category mapToEntity(CategoryDTO categoryDTO, User user);
	
	default Instant now() {
		return Instant.now();
	}
	
	default HashSet<Post> generatePosts() {
		return new HashSet<Post>();
	}
}
