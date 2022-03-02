package com.serrano.app.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serrano.app.forum.domain.Category;
import com.serrano.app.forum.domain.Post;
import com.serrano.app.forum.domain.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);

}
