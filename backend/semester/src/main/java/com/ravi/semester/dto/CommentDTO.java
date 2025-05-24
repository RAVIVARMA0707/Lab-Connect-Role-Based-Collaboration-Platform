package com.ravi.semester.dto;

import com.ravi.semester.model.Comment;

import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String authorId;
    private String authorName;
    private String authorType;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();

        if (comment.getStudent() != null) {
            this.authorId = comment.getStudent().getId();
            this.authorName = comment.getStudent().getName();
            this.authorType="student";
        } else if (comment.getFaculty() != null) {
            this.authorId = comment.getFaculty().getId();
            this.authorName = comment.getFaculty().getName();
            this.authorType="faculty";
        } else if (comment.getIndsPartner() != null) {
            this.authorId = comment.getIndsPartner().getId();
            this.authorName = comment.getIndsPartner().getName();
            this.authorType="IndsPartner";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorType() {
        return authorType;
    }

    public void setAuthorType(String authorType) {
        this.authorType = authorType;
    }
}
