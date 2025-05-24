package com.ravi.semester.service;

import com.ravi.semester.model.*;
import com.ravi.semester.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private PostRepo postRepo;
    @Autowired private CategoryRepo categoryRepo;
    @Autowired private StudentRepo studentRepo;
    @Autowired private FacultyRepo facultyRepo;
    @Autowired private IndsPartnerRepo indsPartnerRepo;
    @Autowired private CommentRepo commentRepo;
    @Autowired private LabRepo labRepo;

    // Create a Category (Faculty Only)
    public Category createCategory(String name, Faculty faculty) {
        if (faculty.getLab() == null) {
            throw new RuntimeException("Faculty is not assigned to any Lab.");
        }
        Category category = new Category();
        category.setName(name);
        category.setLab(faculty.getLab()); // Category is tied to a Lab
        return categoryRepo.save(category);
    }

    public List<Category> getCategoriesByLab(String labId) {
        return categoryRepo.findByLabId(labId);
    }

    public List<Post> getPostsByCategory(Long categoryId) {
        Optional<Category> category = categoryRepo.findById(categoryId);

        if (!category.isPresent()) {
            return Collections.emptyList(); // Return an empty list instead of throwing an exception
        }

        return postRepo.findByCategoryOrderByCreatedAtDesc(category.get());
    }


    // Get Comments for a Post (Sorted by Date & Time)
    public List<Comment> getCommentsByPost(Long postId) {
        Optional<Post> post = postRepo.findById(postId);

        if (!post.isPresent()) {
            return Collections.emptyList(); // Return an empty list if postId is not found
        }

        return commentRepo.findByPostIdOrderByCreatedAtDesc(postId);
    }


    // Create a Post by Faculty, Student, or Industrial Partner
    public Post createPost(String title, String content, LocalDateTime localDateTime, Object user, Category category, String userType) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setCategory(category);
        post.setCreatedAt(localDateTime);

        switch (userType) {
            case "faculty":
                Faculty faculty = (Faculty) user;
                if (!faculty.getLab().equals(category.getLab())) {
                    throw new RuntimeException("This category does not belong to the faculty's Lab.");
                }
                post.setFaculty(faculty);
                break;

            case "student":
                Student student = (Student) user;
                if (!student.getEnrolledLab().equals(category.getLab())) {
                    throw new RuntimeException("This category does not belong to the student's Lab.");
                }
                post.setStudent(student);
                break;

            case "indsPartner":
                IndsPartner indsPartner = (IndsPartner) user;
                if (!indsPartner.getLab().equals(category.getLab())) {
                    throw new RuntimeException("This category does not belong to the Industrial Partner's Lab.");
                }
                post.setIndsPartner(indsPartner);
                break;

            default:
                throw new RuntimeException("Invalid user type.");
        }
        return postRepo.save(post);
    }

    // Add a Comment by Faculty, Student, or Industrial Partner
    public Comment addComment(Long postId, String content, LocalDateTime localDateTime, Object user, String userType) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPost(post);
        comment.setCreatedAt(localDateTime);

        switch (userType) {
            case "faculty":
                Faculty faculty = (Faculty) user;
                if (!faculty.getLab().equals(post.getCategory().getLab())) {
                    throw new RuntimeException("Faculty is not assigned to this lab.");
                }
                comment.setFaculty(faculty);
                break;

            case "student":
                Student student = (Student) user;
                if (!student.getEnrolledLab().equals(post.getCategory().getLab())) {
                    throw new RuntimeException("Student is not enrolled in this lab.");
                }
                comment.setStudent(student);
                break;

            case "indsPartner":
                IndsPartner indsPartner = (IndsPartner) user;
                if (!indsPartner.getLab().equals(post.getCategory().getLab())) {
                    throw new RuntimeException("Industrial Partner is not part of this lab.");
                }
                comment.setIndsPartner(indsPartner);
                break;

            default:
                throw new RuntimeException("Invalid user type.");
        }
        return commentRepo.save(comment);
    }

    // Update Post by Student, Faculty, or Industrial Partner
    public Post updatePost(Long postId, Post updatedPost) {
        Post existingPost = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Check if post belongs to the correct user
        if (updatedPost.getStudent() != null && existingPost.getStudent() != null &&
                !updatedPost.getStudent().getId().equals(existingPost.getStudent().getId())) {
            throw new RuntimeException("Unauthorized to update this post.");
        }
        if (updatedPost.getFaculty() != null && existingPost.getFaculty() != null &&
                !updatedPost.getFaculty().getId().equals(existingPost.getFaculty().getId())) {
            throw new RuntimeException("Unauthorized to update this post.");
        }
        if (updatedPost.getIndsPartner() != null && existingPost.getIndsPartner() != null &&
                !updatedPost.getIndsPartner().getId().equals(existingPost.getIndsPartner().getId())) {
            throw new RuntimeException("Unauthorized to update this post.");
        }

        // Only update non-null fields
        if (updatedPost.getTitle() != null) existingPost.setTitle(updatedPost.getTitle());
        if (updatedPost.getContent() != null) existingPost.setContent(updatedPost.getContent());
        if (updatedPost.getCategory() != null) existingPost.setCategory(updatedPost.getCategory());
        return postRepo.save(existingPost);
    }

    // Update Comment by Student, Faculty, or Industrial Partner
    public Comment updateComment(Long commentId, Comment updatedComment) {
        Comment existingComment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // Check if comment belongs to the correct user
        if (updatedComment.getStudent() != null && existingComment.getStudent() != null &&
                !updatedComment.getStudent().getId().equals(existingComment.getStudent().getId())) {
            throw new RuntimeException("Unauthorized to update this comment.");
        }
        if (updatedComment.getFaculty() != null && existingComment.getFaculty() != null &&
                !updatedComment.getFaculty().getId().equals(existingComment.getFaculty().getId())) {
            throw new RuntimeException("Unauthorized to update this comment.");
        }
        if (updatedComment.getIndsPartner() != null && existingComment.getIndsPartner() != null &&
                !updatedComment.getIndsPartner().getId().equals(existingComment.getIndsPartner().getId())) {
            throw new RuntimeException("Unauthorized to update this comment.");
        }

        // Only update non-null fields
        if (updatedComment.getContent() != null) existingComment.setContent(updatedComment.getContent());
        return commentRepo.save(existingComment);
    }

    // Update Category by Faculty
    public Category updateCategory(Long categoryId, Category updatedCategory, Faculty faculty) {
        Category existingCategory = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Check if the faculty is authorized to update this category
        if (!existingCategory.getLab().getFaculty().getId().equals(faculty.getId())) {
            throw new RuntimeException("Unauthorized to update this category.");
        }

        // Only update non-null fields
        if (updatedCategory.getName() != null) existingCategory.setName(updatedCategory.getName());
        return categoryRepo.save(existingCategory);
    }

    // Delete Category by Faculty
    public void deleteCategory(Long categoryId, String facultyId) {
        Faculty faculty = facultyRepo.findById(facultyId)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Check if the category belongs to the faculty's lab
        if (!category.getLab().getFaculty().getId().equals(facultyId)) {
            throw new RuntimeException("Unauthorized to delete this category.");
        }

        categoryRepo.delete(category);
    }

    // Delete Post by Student, Faculty, or Industrial Partner
    public void deletePost(Long postId, String userId, String userType) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        boolean isAuthorized = false;

        if ("faculty".equalsIgnoreCase(userType)) {
            Faculty faculty = facultyRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Faculty not found"));

            // Faculty can delete any post in their lab
            if (faculty.getLab().equals(post.getCategory().getLab())) {
                isAuthorized = true;
            }
        } else if ("student".equalsIgnoreCase(userType) && post.getStudent() != null) {
            // Student can delete only their own post
            if (post.getStudent().getId().equals(userId)) {
                isAuthorized = true;
            }
        } else if ("indsPartner".equalsIgnoreCase(userType) && post.getIndsPartner() != null) {
            // Industrial Partner can delete only their own post
            if (post.getIndsPartner().getId().equals(userId)) {
                isAuthorized = true;
            }
        }

        if (!isAuthorized) {
            throw new RuntimeException("Unauthorized to delete this post.");
        }

        postRepo.delete(post);
    }

    // Delete Comment by Student, Faculty, or Industrial Partner
    public void deleteComment(Long commentId, String userId, String userType) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        boolean isAuthorized = false;

        if ("faculty".equalsIgnoreCase(userType)) {
            Faculty faculty = facultyRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Faculty not found"));

            // Faculty can delete any comment in their lab
            if (faculty.getLab().equals(comment.getPost().getCategory().getLab())) {
                isAuthorized = true;
            }
        } else if ("student".equalsIgnoreCase(userType) && comment.getStudent() != null) {
            // Student can delete only their own comment
            if (comment.getStudent().getId().equals(userId)) {
                isAuthorized = true;
            }
        } else if ("indsPartner".equalsIgnoreCase(userType) && comment.getIndsPartner() != null) {
            // Industrial Partner can delete only their own comment
            if (comment.getIndsPartner().getId().equals(userId)) {
                isAuthorized = true;
            }
        }

        if (!isAuthorized) {
            throw new RuntimeException("Unauthorized to delete this comment.");
        }

        commentRepo.delete(comment);
    }

}
