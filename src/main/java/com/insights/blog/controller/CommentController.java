package com.insights.blog.controller;

import com.insights.blog.model.Comment;
import com.insights.blog.model.User;
import com.insights.blog.payload.CommentRequestDTO;
import com.insights.blog.payload.CommentResponseDTO;
import com.insights.blog.security.CurrentUser;
import com.insights.blog.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public List<CommentResponseDTO> getCommentsById(@RequestParam Integer blogId) {
        return commentService.getCommentsById(blogId);
    }

    @PostMapping("/create/comment/{blogId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createComment(@PathVariable Integer blogId, @RequestBody CommentRequestDTO commentRequestDTO, @CurrentUser User currentUser) {
        commentService.addComment(blogId, commentRequestDTO.getComment(), currentUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/comment/{commentId}/{blogId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteComment(@PathVariable Integer blogId, @PathVariable Integer commentId, @CurrentUser User currentUser) {
        commentService.deleteComment(blogId, commentId, currentUser);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/comment/{commentId}/{blogId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateComment(@PathVariable Integer blogId, @RequestBody CommentRequestDTO commentRequestDTO, @PathVariable Integer commentId, @CurrentUser User currentUser) {
        commentService.updateComment(blogId, commentId, commentRequestDTO.getComment(), currentUser);
        return ResponseEntity.ok().build();
    }
}
