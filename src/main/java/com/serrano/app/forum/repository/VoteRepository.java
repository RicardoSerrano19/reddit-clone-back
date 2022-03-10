package com.serrano.app.forum.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serrano.app.forum.domain.Post;
import com.serrano.app.forum.domain.User;
import com.serrano.app.forum.domain.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{
	// Find Vote by Post And User
	// Order by Desc
	// Get only one
	Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User user);
}
