package com.serrano.app.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serrano.app.forum.domain.Comment;
import com.serrano.app.forum.domain.Post;
import com.serrano.app.forum.domain.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findByPost(Post post);
	Long countByPost(Post post);
	List<Comment> findByUser(User user);
}
