package com.serrano.app.forum.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
import com.serrano.app.forum.mapper.PostMapper;
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
	private final PostMapper mapper;
	
	@Transactional
	public ServiceResponse create(PostDTO postDTO) {
		Category category = categoryRepo.findByName(postDTO.getCategory()).orElseThrow(() -> new CategoryNotFoundException(postDTO.getCategory()));
		User user = authService.getCurrentUser();
		try {
			Post post = mapper.mapToEntity(postDTO, category, user);
			Post postSaved = postRepo.save(post);
			PostDTO postDTOResponse = mapper.mapToDTO(postSaved);
			return new ServiceResponse("Post registration succesfully", HttpStatus.CREATED, postDTOResponse);			

		}catch (Exception ex) {
			throw new CustomApiException("Something went wrong when try to create post", 
					new ArrayList<String>(Arrays.asList(ex.getMessage())) );
		}
	}
	
	@Transactional(readOnly = true)
	public PostsDTO getAll() {
        List<Post> entity = postRepo.findAll();																																																																																																																																																																																																																																																																																																																																																																																																														
        List<PostDTO> categoriesDTO = entity.stream()
        		.map(c -> mapper.mapToDTO(c))
        		.collect(Collectors.toList());
        PostsDTO posts = new PostsDTO(categoriesDTO);
		return posts;
	}
	
	@Transactional(readOnly = true)
	public PostDTO findById(Long id) {
		Post post = postRepo.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		log.info(post.toString());
		PostDTO postDTO = mapper.mapToDTO(post);
		return postDTO;
	}
	
	@Transactional(readOnly = true)
	public PostsDTO findByCategory(String name) {
		Category category = categoryRepo.findByName(name).orElseThrow(() -> new CategoryNotFoundException(name));
		log.info(category.toString());
		try {
			List<Post> post = postRepo.findByCategory(category);
	        List<PostDTO> posts = post.stream()
	        		.map(p -> mapper.mapToDTO(p))
	        		.collect(Collectors.toList());

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
	        List<PostDTO> posts = post.stream()
	        		.map(p -> mapper.mapToDTO(p))
	        		.collect(Collectors.toList());
			PostsDTO postsDTO = new PostsDTO(posts);
			return postsDTO;
		}catch(Exception ex) {
			throw new CustomApiException("Something went wrong when try to create post", 
					new ArrayList<String>(Arrays.asList(ex.getMessage())) );
		}
	}
	
}
