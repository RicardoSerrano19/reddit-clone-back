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

import com.serrano.app.forum.dto.CommentDTO;
import com.serrano.app.forum.service.CommentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
	
	private final CommentService commentService;
	
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody CommentDTO comment){
		return new ResponseEntity<Object>(commentService.create(comment), HttpStatus.CREATED);
	}
	
	@GetMapping(path = "/posts/{id}")
	public ResponseEntity<Object> getAllCommentsForPost(@PathVariable Long id){
		return new ResponseEntity<Object>(commentService.getCommentsByPost(id), HttpStatus.OK);
	}
	
	@GetMapping(path= "/users/{username}")
	public ResponseEntity<Object> getCommentsByUsername(@PathVariable String username){
		return new ResponseEntity<Object>(commentService.getCommentsByUsername(username), HttpStatus.OK);
	}

}
