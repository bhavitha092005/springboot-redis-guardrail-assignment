package com.assignment.dto;

public class CommentRequest {

    private Long authorId;
    private String authorType;
    private String content;
    private int depthLevel;

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
}