package com.serrano.app.forum.mapper;

import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.serrano.app.forum.domain.User;
import com.serrano.app.forum.dto.RegisterRequest;

@Mapper(componentModel = "spring")
public interface AuthMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "roles", ignore = true)
	@Mapping(target = "created_at", expression = "java(now())")
	@Mapping(target = "password", source = "password")
	@Mapping(target = "enabled", constant = "false")
	User mapToEntity(RegisterRequest request, String password);
	
	default Instant now() {
		return Instant.now();
	}
	
}
