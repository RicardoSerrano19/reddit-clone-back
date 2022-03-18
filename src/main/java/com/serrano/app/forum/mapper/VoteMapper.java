package com.serrano.app.forum.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.serrano.app.forum.domain.Post;
import com.serrano.app.forum.domain.User;
import com.serrano.app.forum.domain.Vote;
import com.serrano.app.forum.dto.VoteDTO;
import com.serrano.app.forum.enums.VoteType;

@Mapper(componentModel = "spring")
public interface VoteMapper {

	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "post", source = "post")
	@Mapping(target = "user", source = "user")
	@Mapping(target = "vote", source = "voteType")
	Vote mapToEntity(VoteDTO voteDTO,Post post, User user, VoteType voteType);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "post", source = "post")
	@Mapping(target = "user", source = "user")
	@Mapping(target = "vote", source = "voteType")
	Vote mapToEntity(Vote vote,Post post, User user, VoteType voteType);
	
	
	@InheritInverseConfiguration
	@Mapping(target = "postId", source = "post.id")
	@Mapping(target = "voteType", source = "vote")
	VoteDTO mapToDTO(Vote vote);
}
