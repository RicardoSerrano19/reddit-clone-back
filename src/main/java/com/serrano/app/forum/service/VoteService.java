package com.serrano.app.forum.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serrano.app.forum.domain.Post;
import com.serrano.app.forum.domain.User;
import com.serrano.app.forum.domain.Vote;
import com.serrano.app.forum.dto.ServiceResponse;
import com.serrano.app.forum.dto.VoteDTO;
import com.serrano.app.forum.enums.VoteType;
import com.serrano.app.forum.exception.CustomApiException;
import com.serrano.app.forum.exception.PostNotFoundException;
import com.serrano.app.forum.exception.VotePreviouslyAssignedException;
import com.serrano.app.forum.repository.PostRepository;
import com.serrano.app.forum.repository.VoteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteService {

	private final VoteRepository voteRepo;
	private final PostRepository postRepo;
	private final AuthService authService;
	private final ModelMapper mapper;
	
	@PostConstruct
	private void init() {
	    TypeMap<VoteDTO, Vote> propertyMapper = mapper.createTypeMap(VoteDTO.class, Vote.class);
	    propertyMapper.addMappings(mapper -> mapper.skip(Vote::setPost));
	    propertyMapper.addMappings(mapper -> mapper.skip(Vote::setId));

	}
	
	@Transactional
	public ServiceResponse vote(VoteDTO voteDTO){
		Post post = postRepo.findById(voteDTO.getPostId()).orElseThrow(() -> new PostNotFoundException(voteDTO.getPostId()));
		User user = authService.getCurrentUser();
		Optional<Vote> voteByPostAndUser = voteRepo.findTopByPostAndUserOrderByIdDesc(post,  user);
		
		if(isSameVote(voteByPostAndUser, voteDTO)) throw new VotePreviouslyAssignedException(voteDTO.getVoteType().name());

		int quantity = calculateQuantity(voteByPostAndUser, voteDTO.getVoteType());
		post.setVotes(post.getVotes() + quantity);
		
		try {
			Vote vote = currentVote(voteByPostAndUser, voteDTO);
			vote.setVote(voteDTO.getVoteType());
			vote.setPost(post);		
			vote.setUser(user);
			
			Vote voteSaved = voteRepo.save(vote);
			postRepo.save(post);
			VoteDTO voteDTOResponse = mapper.map(voteSaved, VoteDTO.class);
			return new ServiceResponse("Vote registration succesfully", HttpStatus.CREATED, voteDTOResponse);			
		}catch(Exception ex) {
			throw new CustomApiException("Something went wrong when try to create vote", 
					new ArrayList<String>(Arrays.asList(ex.getMessage())) );
		}
	}
	
	private boolean isSameVote(Optional<Vote> vote, VoteDTO voteDTO) {
		return (vote.isPresent() && vote.get().getVote().equals(voteDTO.getVoteType()));
	}
	
	private Vote currentVote(Optional<Vote> vote, VoteDTO voteDTO) {
		if(vote.isPresent()) {
			return vote.get();
		}else {
			Vote newVote = mapper.map(voteDTO, Vote.class);
			return newVote;
		}
	}
	
	private int calculateQuantity(Optional<Vote> vote, VoteType voteType) {
		if(vote.isEmpty()) {
			if(VoteType.UP.equals(voteType)) return 1;
			else return -1;
		}else {
			if(VoteType.UP.equals(voteType)) return 2;
			else return -2;
		}
	}

}
