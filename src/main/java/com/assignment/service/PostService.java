package com.assignment.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.assignment.dto.CommentRequest;
import com.assignment.dto.PostRequest;
import com.assignment.entity.Comment;
import com.assignment.entity.Post;
import com.assignment.exception.ApiException;
import com.assignment.repository.CommentRepository;
import com.assignment.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RedisService redisService;
    
    @Autowired
    private NotificationService notificationService;

    public Post createPost(PostRequest request) {

        Post post = new Post(
                request.getAuthorId(),
                request.getAuthorType(),
                request.getContent());

        return postRepository.save(post);
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Comment addComment(Long postId, CommentRequest request) {

        if (request.getDepthLevel() > 20) {
            throw new ApiException(
                    "Depth level exceeded maximum limit",
                    HttpStatus.BAD_REQUEST);
        }

        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isEmpty()) {
            throw new ApiException(
                    "Post not found",
                    HttpStatus.NOT_FOUND);
        }

        Post post = optionalPost.get();

        if (request.getAuthorType().equalsIgnoreCase("BOT")) {

            String botCountKey = "post:" + postId + ":bot_count";

            Long count = redisService.incrementCounter(botCountKey);

            if (count > 100) {
                throw new ApiException(
                        "Too Many Bot Replies",
                        HttpStatus.TOO_MANY_REQUESTS);
            }

            String cooldownKey =
                    "cooldown:bot_" +
                    request.getAuthorId() +
                    ":human_" +
                    post.getAuthorId();

            boolean allowed =
                    redisService.setCooldown(cooldownKey, 10);

            if (!allowed) {
                throw new ApiException(
                        "Bot is on cooldown for this user",
                        HttpStatus.TOO_MANY_REQUESTS);
            }

            notificationService.handleBotInteraction(post.getAuthorId(), request.getAuthorId());
        }

        if (request.getAuthorType().equalsIgnoreCase("USER")) {
            redisService.incrementVirality(postId, 50);
        }

        Comment comment = new Comment(
                postId,
                request.getAuthorId(),
                request.getAuthorType(),
                request.getContent(),
                request.getDepthLevel());

        return commentRepository.save(comment);
    }

    public Post likePost(Long postId) {

        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {

            Post post = optionalPost.get();

            post.setLikes(post.getLikes() + 1);

            redisService.incrementVirality(postId, 20);

            return postRepository.save(post);
        }

        return null;
    }
}