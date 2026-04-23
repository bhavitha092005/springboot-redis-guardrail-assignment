package com.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.assignment.dto.CommentRequest;
import com.assignment.dto.PostRequest;
import com.assignment.entity.Comment;
import com.assignment.entity.Post;
import com.assignment.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostRequest request) {

        return ResponseEntity.ok(postService.createPost(request));
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest request) {

        return ResponseEntity.ok(postService.addComment(postId, request));
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Post> likePost(@PathVariable Long postId) {

        Post post = postService.likePost(postId);

        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(post);
    }
    
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {

        Post post = postService.getPost(postId);

        if(post == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(post);
    }
}