package com.ravi.semester.controller;

import com.ravi.semester.dto.CategoryRequestDto;
import com.ravi.semester.dto.CommentDTO;
import com.ravi.semester.dto.PostDTO;
import com.ravi.semester.model.*;
import com.ravi.semester.repository.*;
import com.ravi.semester.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pic/blogs")
@CrossOrigin
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired private FacultyRepo facultyRepo;
    @Autowired private StudentRepo studentRepo;
    @Autowired private IndsPartnerRepo indsPartnerRepo;
    @Autowired private PostRepo postRepo;
    @Autowired private CategoryRepo categoryRepo;
    @Autowired private LabRepo labRepo;

    // Create a Category (Faculty Only)
    @PostMapping("/category/create")
    public ResponseEntity<Void> createCategory(@RequestBody CategoryRequestDto requestDto) {
        Faculty faculty = facultyRepo.findById(requestDto.getFacultyId()).orElse(null);
        if (faculty == null) return ResponseEntity.badRequest().build();
        blogService.createCategory(requestDto.getName(), faculty);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/categories/{labId}")
    public ResponseEntity<List<Category>> getCategoriesByLab(@PathVariable String labId) {
        List<Category> categories = blogService.getCategoriesByLab(labId);
        return ResponseEntity.ok(categories);
    }

    // Get All Posts for a Specific Category (Sorted)
    @GetMapping("/posts/category/{categoryId}")
    public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Long categoryId) {
        List<Post> posts = blogService.getPostsByCategory(categoryId);

        if (posts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<PostDTO> postDTOs = posts.stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postDTOs);
    }


    @GetMapping("/comments/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = blogService.getCommentsByPost(postId);

        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<CommentDTO> commentDTOs = comments.stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOs);
    }

    // Create a Post by Faculty, Student, or Industrial Partner
    @PostMapping("/post/create/{userType}")
    public ResponseEntity<Post> createPost(
            @PathVariable String userType,
            @RequestBody Post post
    ) {
//        System.out.println(userType);
        switch (userType) {
            case "faculty":
                Faculty faculty = facultyRepo.findById(post.getFaculty().getId()).orElse(null);
                Category facultyCategory = categoryRepo.findById(post.getCategory().getId()).orElse(null);
                if (faculty == null || facultyCategory == null) return ResponseEntity.badRequest().build();
                blogService.createPost(post.getTitle(), post.getContent(), post.getCreatedAt(), faculty, facultyCategory, "faculty");
                break;

            case "student":
                Student student = studentRepo.findById(post.getStudent().getId()).orElse(null);
                Category studentCategory = categoryRepo.findById(post.getCategory().getId()).orElse(null);
                if (student == null || studentCategory == null) return ResponseEntity.badRequest().build();
                blogService.createPost(post.getTitle(), post.getContent(), post.getCreatedAt(), student, studentCategory, "student");
                break;

            case "indsPartner":
                IndsPartner indsPartner = indsPartnerRepo.findById(post.getIndsPartner().getId()).orElse(null);
                Category indsCategory = categoryRepo.findById(post.getCategory().getId()).orElse(null);
                if (indsPartner == null || indsCategory == null) return ResponseEntity.badRequest().build();
                blogService.createPost(post.getTitle(), post.getContent(), post.getCreatedAt(), indsPartner, indsCategory, "indsPartner");
                break;

            default:
                return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(post);
    }

    // Create a Comment by Faculty, Student, or Industrial Partner
    @PostMapping("/comment/{userType}")
    public ResponseEntity<Comment> createComment(
            @PathVariable String userType,
            @RequestBody Comment comment
    ) {
        switch (userType) {
            case "faculty":
                Faculty faculty = facultyRepo.findById(comment.getFaculty().getId()).orElse(null);
                if (faculty == null) return ResponseEntity.badRequest().build();
                blogService.addComment(comment.getPost().getId(), comment.getContent(), comment.getCreatedAt(), faculty, "faculty");
                break;

            case "student":
                Student student = studentRepo.findById(comment.getStudent().getId()).orElse(null);
                if (student == null) return ResponseEntity.badRequest().build();
                blogService.addComment(comment.getPost().getId(), comment.getContent(), comment.getCreatedAt(), student, "student");
                break;

            case "indsPartner":
                IndsPartner indsPartner = indsPartnerRepo.findById(comment.getIndsPartner().getId()).orElse(null);
                if (indsPartner == null) return ResponseEntity.badRequest().build();
                blogService.addComment(comment.getPost().getId(), comment.getContent(), comment.getCreatedAt(), indsPartner, "indsPartner");
                break;

            default:
                return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/post/update/{postId}")
    public ResponseEntity<Post> updatePost(
            @PathVariable Long postId,
            @RequestBody Post updatedPost
    ) {
        Post post = blogService.updatePost(postId, updatedPost);
        return ResponseEntity.ok(post);
    }

    // Update Comment by Any User
    @PutMapping("/comment/update/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long commentId,
            @RequestBody Comment updatedComment
    ) {
        Comment comment = blogService.updateComment(commentId, updatedComment);
        return ResponseEntity.ok(comment);
    }

    // Update Category by Faculty
    @PutMapping("/category/update/{facultyId}/{categoryId}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable String facultyId,
            @PathVariable Long categoryId,
            @RequestBody Category updatedCategory
    ) {
        Faculty faculty = facultyRepo.findById(facultyId).orElseThrow(() -> new RuntimeException("Faculty not found"));
        Category category = blogService.updateCategory(categoryId, updatedCategory, faculty);
        return ResponseEntity.ok(category);
    }

    // Delete Category by Faculty
    @DeleteMapping("/category/delete/{facultyId}/{categoryId}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable String facultyId,
            @PathVariable Long categoryId
    ) {
        blogService.deleteCategory(categoryId, facultyId);
        return ResponseEntity.ok("Category deleted successfully.");
    }

    // Delete Post by Any User
    @DeleteMapping("/post/delete/{userId}/{postId}/{userType}")
    public ResponseEntity<String> deletePost(
            @PathVariable String userId,
            @PathVariable Long postId,
            @PathVariable String userType
    ) {
        blogService.deletePost(postId, userId, userType);
        return ResponseEntity.ok("Post deleted successfully.");
    }

    // Delete Comment by Any User
    @DeleteMapping("/comment/delete/{userId}/{commentId}/{userType}")
    public ResponseEntity<String> deleteComment(
            @PathVariable String userId,
            @PathVariable Long commentId,
            @PathVariable String userType
    ) {
        blogService.deleteComment(commentId, userId, userType);
        return ResponseEntity.ok("Comment deleted successfully.");
    }
}

