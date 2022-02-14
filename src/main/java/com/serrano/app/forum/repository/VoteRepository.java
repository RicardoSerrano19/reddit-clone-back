package com.serrano.app.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.serrano.app.forum.domain.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{

}
