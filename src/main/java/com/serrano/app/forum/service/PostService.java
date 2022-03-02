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

import com.serrano.app.forum.domain.Category;
import com.serrano.app.forum.domain.Post;
import com.serrano.app.forum.domain.User;
import com.serrano.app.forum.dto.PostDTO;
import com.serrano.app.forum.dto.PostsDTO;
import com.serrano.app.forum.dto.ServiceResponse;
import com.serrano.app.forum.exception.CategoryNotFoundException;
import com.serrano.app.forum.exception.CustomApiException;
import com.serrano.app.forum.exception.PostNotFoundException;
import com.serrano.app.forum.exception.UsernameNotFoundException;
import com.serrano.app.forum.repository.CategoryRepository;
import com.serrano.app.forum.repository.PostRepository;
import com.serrano.app.forum.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
	private final PostRepository postRepo;
	private final UserRepository userRepo;
	private final CategoryRepository categoryRepo;
	private final AuthService authService;
	private final ModelMapper mapper;
	
	@PostConstruct
	private void init() {
	    TypeMap<PostDTO, Post> propertyMapper = mapper.createTypeMap(PostDTO.class, Post.class);
	    propertyMapper.addMappings(mapper -> mapper.skip(Post::setId));
	    
	    TypeMap<Post, PostDTO> propertyMapperDTO = mapper.createTypeMap(Post.class, PostDTO.class);
	    propertyMapperDTO.addMappings(mapper -> mapper.map(src -> src.getCategory().getName(), PostDTO::setCategory));

    }
	
	@Transactional
	public ServiceResponse create(PostDTO postDTO) {
		Category category = categoryRepo.findByName(postDTO.getCategory()).orElseThrow(() -> new CategoryNotFoundException(postDTO.getCategory()));
		User user = authService.getCurrentUser();
		try {
			Post post = mapper.map(postDTO, Post.class);
			post.setUser(user);
			post.setCategory(category);
			post.setCreated_at(Instant.now());
			Post postSaved = postRepo.save(post);
			PostDTO postDTOResponse = mapper.map(postSaved, PostDTO.class);
			return new ServiceResponse("Post registration succesfully", HttpStatus.CREATED, postDTOResponse);			

		}catch (Exception ex) {
			throw new CustomApiException("Something went wrong when try to create post", 
					new ArrayList<String>(Arrays.asList(ex.getMessage())) );
		}
	}
	
	@Transactional(readOnly = true)
	public PostsDTO getAll() {
        List<Post> entity = postRepo.findAll();																																																																																																																																																																																																																																																																																																																																																																																																														
        List<PostDTO> categoriesDTO = mapper.map(entity, new TypeToken<List<PostDTO>>(){}.getType());
        PostsDTO posts = new PostsDTO(categoriesDTO);
		return posts;
	}
	
	@Transactional(readOnly = true)
	public PostDTO findById(Long id) {
		Post post = postRepo.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		log.info(post.toString());
		PostDTO postDTO = mapper.map(post, PostDTO.class);
		return postDTO;
	}
	
	@Transactional(readOnly = true)
	public PostsDTO findByCategory(String name) {
		Category category = categoryRepo.findByName(name).orElseThrow(() -> new CategoryNotFoundException(name));
		log.info(category.toString());
		try {
			List<Post> post = postRepo.findByCategory(category);
	        List<PostDTO> posts = mapper.map(post, new TypeToken<List<PostDTO>>(){}.getType());

			PostsDTO postsDTO = new PostsDTO(posts);
			return postsDTO;
		}catch(Exception ex) {
			throw new CustomApiException("Something went wrong when try to create post", 
					new ArrayList<String>(Arrays.asList(ex.getMessage())) );
		}
	}
	
	@Transactional(readOnly = true)
	public PostsDTO findByUsername(String username) {
		User user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
		try {
			List<Post> post = postRepo.findByUser(user);
	        List<PostDTO> posts = mapper.map(post, new TypeToken<List<PostDTO>>(){}.getType());
			PostsDTO postsDTO = new PostsDTO(posts);
			return postsDTO;
		}catch(Exception ex) {
			throw new CustomApiException("Something went wrong when try to create post", 
					new ArrayList<String>(Arrays.asList(ex.getMessage())) );
		}
	}
	
}
