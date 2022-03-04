package com.serrano.app.forum.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serrano.app.forum.domain.Comment;
import com.serrano.app.forum.domain.Post;
import com.serrano.app.forum.domain.User;
import com.serrano.app.forum.dto.CommentDTO;
import com.serrano.app.forum.dto.Comments;
import com.serrano.app.forum.dto.ServiceResponse;
import com.serrano.app.forum.exception.CustomApiException;
import com.serrano.app.forum.exception.PostNotFoundException;
import com.serrano.app.forum.exception.UsernameNotFoundException;
import com.serrano.app.forum.repository.CommentRepository;
import com.serrano.app.forum.repository.PostRepository;
import com.serrano.app.forum.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

	private final CommentRepository commentRepo;
	private final ModelMapper mapper;
	private final PostRepository postRepo;
	private final AuthService authService;
	private final UserRepository userRepo;
	
	@PostConstruct
	private void init() {
	    TypeMap<CommentDTO, Comment> propertyMapper = mapper.createTypeMap(CommentDTO.class, Comment.class);
	    propertyMapper.addMappings(mapper -> mapper.skip(Comment::setId));
	    propertyMapper.addMappings(mapper -> mapper.skip(Comment::setCrated_at));
	    
	    TypeMap<Comment, CommentDTO> propertyMapperDTO = mapper.createTypeMap(Comment.class, CommentDTO.class);
	    propertyMapperDTO.addMappings(mapper -> mapper.map(src -> src.getUser().getUsername(), CommentDTO::setUsername));

	}
	
	@Transactional
	public ServiceResponse create(CommentDTO commentDTO) {
		log.info(commentDTO.toString());
		Post post = postRepo.findById(commentDTO.getPostId()).orElseThrow(() -> new PostNotFoundException(commentDTO.getPostId()));
		log.info(commentDTO.toString());
		User user = authService.getCurrentUser();
		
		try {
			Comment comment = mapper.map(commentDTO, Comment.class);
			comment.setCrated_at(Instant.now());
			comment.setUser(user);
			comment.setPost(post);
			Comment commentSaved = commentRepo.save(comment);
			
			CommentDTO commentDTOResponse = mapper.map(commentSaved, CommentDTO.class);
			
			return new ServiceResponse("Comment registration succesfully", HttpStatus.CREATED, commentDTOResponse);			
		} catch (Exception ex) {
			throw new CustomApiException("Something went wrong when try to create comment", 
					new ArrayList<String>(Arrays.asList(ex.getMessage())) );
		}
	}

	public Comments getCommentsByPost(Long id) {
		Post post = postRepo.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		List<Comment> comments = commentRepo.findByPost(post);
        List<CommentDTO> commentDTO = mapper.map(comments, new TypeToken<List<CommentDTO>>(){}.getType());
        Comments commentsDTO = new Comments(commentDTO);
		return commentsDTO;
	}

	public Comments getCommentsByUsername(String username) {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		List<Comment> comments = commentRepo.findByUser(user);
		List<CommentDTO> commentDTO = mapper.map(comments, new TypeToken<List<CommentDTO>>(){}.getType());
		Comments commentsDTO = new Comments(commentDTO);
		return commentsDTO;
	}
}
