package com.insights.blog.controller;

import com.insights.blog.entity.Blog;
import com.insights.blog.payload.BlogRequestDTO;
import com.insights.blog.payload.BlogResponseDTO;
import com.insights.blog.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    public ResponseEntity<List<Blog>> getAllPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/create/blog")
    @PreAuthorize("hasRole('USER')")
    public BlogResponseDTO createBlog(@RequestBody BlogRequestDTO blogRequestDTO) {
        return postService.addBlog(blogRequestDTO);
    }
}
