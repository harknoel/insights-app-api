package com.insights.blog.service;

import com.insights.blog.exception.BlogNotFoundException;
import com.insights.blog.exception.CommentNotFoundException;
import com.insights.blog.exception.UnauthorizedActionException;
import com.insights.blog.model.Blog;
import com.insights.blog.model.Comment;
import com.insights.blog.model.User;
import com.insights.blog.payload.BlogResponseDTO;
import com.insights.blog.payload.CommentResponseDTO;
import com.insights.blog.payload.UserDTO;
import com.insights.blog.payload.UserWithEmailDTO;
import com.insights.blog.repository.CommentRepository;
import com.insights.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    public void addComment(Integer blogId, String content, User currentUser) {
        Optional<Blog> optionalBlog = blogRepository.findById(blogId);

        // Check if the blog exists
        if (optionalBlog.isPresent()) {
            Blog blog = optionalBlog.get();
            var comment = Comment
                    .builder()
                    .user(currentUser)
                    .blog(blog)
                    .comment(content)
                    .build();
            commentRepository.save(comment);
        } else {
            throw new BlogNotFoundException(blogId);
        }
    }

    public void deleteComment(Integer blogId, Integer commentId, User currentUser) {
        try {
            // Check if the blog exists
            Optional<Blog> optionalBlog = blogRepository.findById(blogId);
            if (optionalBlog.isEmpty()) {
                throw new BlogNotFoundException(blogId);
            }

            Blog blog = optionalBlog.get();
            // Check if the comment exists in the blog
            Optional<Comment> optionalComment = commentRepository.findById(commentId);
            if (optionalComment.isEmpty()) {
                throw new CommentNotFoundException(commentId);
            }

            Comment comment = optionalComment.get();

            // Check if the comment belongs to the specified blog
            if (!comment.getBlog().getBlogId().equals(blogId)) {
                throw new IllegalArgumentException("Comment with ID " + commentId + " does not belong to blog with ID " + blogId);
            }

            // Check if the user is authorized to delete the comment
            int commentUserId = comment.getUser().getUserId();
            int currentUserId = currentUser.getUserId();

            if (commentUserId == currentUserId) {
                commentRepository.deleteById(commentId);
            } else {
                throw new UnauthorizedActionException("You are not authorized to delete this comment");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete comment", e);
        }
    }

    public void updateComment(Integer blogId, Integer commentId, String content, User currentUser) {
        try {
            // Check if the blog exists
            Optional<Blog> optionalBlog = blogRepository.findById(blogId);
            if (optionalBlog.isEmpty()) {
                throw new BlogNotFoundException(blogId);
            }

            Blog blog = optionalBlog.get();
            // Check if the comment exists in the blog
            Optional<Comment> optionalComment = commentRepository.findById(commentId);
            if (optionalComment.isEmpty()) {
                throw new CommentNotFoundException(commentId);
            }

            Comment comment = optionalComment.get();

            // Check if the comment belongs to the specified blog
            if (!comment.getBlog().getBlogId().equals(blogId)) {
                throw new IllegalArgumentException("Comment with ID " + commentId + " does not belong to blog with ID " + blogId);
            }

            // Check if the user is authorized to update the comment
            int commentUserId = comment.getUser().getUserId();
            int currentUserId = currentUser.getUserId();

            if (commentUserId == currentUserId) {
                // Update the content of the comment
                comment.setComment(content);
                commentRepository.save(comment);
            } else {
                throw new UnauthorizedActionException("You are not authorized to update this comment");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update comment", e);
        }
    }

    public List<CommentResponseDTO> getCommentsById(Integer blogId) {
        Optional<Blog> optionalBlog = blogRepository.findById(blogId);
        if(optionalBlog.isEmpty()) {
            throw new BlogNotFoundException(blogId);
        }

        Blog blog = optionalBlog.get();

        // Process comments and convert them to CommentResponseDTO objects
        List<CommentResponseDTO> commentDtos = new ArrayList<>();
        for (Comment comment : blog.getComments()) {
            commentDtos.add(buildCommentResponseDTO(comment)); // Pass the comment object
        }
        return commentDtos;
    }

    private CommentResponseDTO buildCommentResponseDTO(Comment comment) {
        UserWithEmailDTO user = new UserWithEmailDTO(comment.getUser().getUserId(), comment.getUser().getFirstname(), comment.getUser().getLastname(), comment.getUser().getEmail());

        return CommentResponseDTO.builder()
                .comment(comment.getComment())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .user(user)
                .build();
    }
}
