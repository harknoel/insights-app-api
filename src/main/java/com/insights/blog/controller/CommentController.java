package com.insights.blog.controller;

import com.insights.blog.model.Comment;
import com.insights.blog.model.User;
import com.insights.blog.payload.CommentRequestDTO;
import com.insights.blog.security.CurrentUser;
import com.insights.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
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
}
