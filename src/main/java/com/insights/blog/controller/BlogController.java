package com.insights.blog.controller;

import com.insights.blog.entity.Blog;
import com.insights.blog.payload.BlogRequestDTO;
import com.insights.blog.payload.BlogResponseDTO;
import com.insights.blog.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    public ResponseEntity<List<Blog>> getAllPosts() {
        return blogService.getAllPosts();
    }

    @GetMapping("/create/blog")
    @PreAuthorize("hasRole('USER')")
    public BlogResponseDTO createBlog(@RequestBody BlogRequestDTO blogRequestDTO) {
        return blogService.addBlog(blogRequestDTO);
    }
}
