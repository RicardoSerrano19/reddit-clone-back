package com.serrano.app.forum.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serrano.app.forum.dto.PostDTO;
import com.serrano.app.forum.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
	private final PostService postService;
	
	@PostMapping
	public ResponseEntity<Object> createPost(@Valid @RequestBody PostDTO post) {
		return new ResponseEntity<Object>(postService.create(post), HttpStatus.CREATED);
	}
	
	@GetMapping("")
	public ResponseEntity<Object> findAll(){
		return new ResponseEntity<Object>(postService.getAll(), HttpStatus.OK);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id){
		return new ResponseEntity<Object>(postService.findById(id), HttpStatus.OK);
	}
	
	@GetMapping("/users/{username}")
	public ResponseEntity<Object> findByUsername(@PathVariable String username){
		return new ResponseEntity<Object>(postService.findByUsername(username), HttpStatus.OK);
	}
	
	@GetMapping("/categories/{category}")
	public ResponseEntity<Object> findByCategory(@PathVariable String category){
		return new ResponseEntity<Object>(postService.findByCategory(category), HttpStatus.OK);
	}
	
}
