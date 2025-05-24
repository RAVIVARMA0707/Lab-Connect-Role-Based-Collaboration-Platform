package com.ravi.semester.dto;

import com.ravi.semester.model.Post;

import java.time.LocalDateTime;

public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String authorId;
    private String authorName;
    private String authorType;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();

        if (post.getStudent() != null) {
            this.authorId = post.getStudent().getId();
            this.authorName = post.getStudent().getName();
            this.authorType="student";
        } else if (post.getFaculty() != null) {
            this.authorId = post.getFaculty().getId();
            this.authorName = post.getFaculty().getName();
            this.authorType="faculty";
        } else if (post.getIndsPartner() != null) {
            this.authorId = post.getIndsPartner().getId();
            this.authorName = post.getIndsPartner().getName();
            this.authorType="IndsPartner";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
