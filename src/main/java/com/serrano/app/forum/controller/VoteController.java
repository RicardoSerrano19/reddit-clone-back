package com.serrano.app.forum.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.serrano.app.forum.dto.VoteDTO;
import com.serrano.app.forum.service.VoteService;
import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VoteController {

	private final VoteService voteService;
	
	@PostMapping
	public ResponseEntity<Object> vote(@Valid @RequestBody VoteDTO vote){
		return new ResponseEntity<Object>(voteService.vote(vote), HttpStatus.OK);
	}
}
