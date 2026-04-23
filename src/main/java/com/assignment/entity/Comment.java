package com.assignment.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    private Long authorId;

    private String authorType;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int depthLevel;

    private LocalDateTime createdAt;

    public Comment() {
        this.createdAt = LocalDateTime.now();
    }

    public Comment(Long postId, Long authorId, String authorType, String content, int depthLevel) {
        this.postId = postId;
        this.authorId = authorId;
        this.authorType = authorType;
        this.content = content;
        this.depthLevel = depthLevel;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getPostId() {
        return postId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getAuthorType() {
        return authorType;
    }

    public String getContent() {
        return content;
    }

    public int getDepthLevel() {
        return depthLevel;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public void setAuthorType(String authorType) {
        this.authorType = authorType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDepthLevel(int depthLevel) {
        this.depthLevel = depthLevel;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}