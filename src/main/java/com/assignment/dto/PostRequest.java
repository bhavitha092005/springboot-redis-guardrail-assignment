package com.assignment.dto;

public class PostRequest {

    private Long authorId;
    private String authorType;
    private String content;

    public Long getAuthorId() {
        return authorId;
    }

    public String getAuthorType() {
        return authorType;
    }

    public String getContent() {
        return content;
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
}