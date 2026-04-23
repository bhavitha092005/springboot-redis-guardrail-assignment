package com.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.assignment.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}