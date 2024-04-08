package com.insights.blog.controller;

import com.insights.blog.entity.Blog;
import com.insights.blog.payload.PostRequestDTO;
import com.insights.blog.payload.PostResponseDTO;
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
    public PostResponseDTO createBlog(@RequestBody PostRequestDTO postRequestDTO) {
        return postService.addBlog(postRequestDTO);
    }

    @DeleteMapping("/delete/blog/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteBlog(@PathVariable Integer id) {
        boolean status = postService.deleteBlog(id);
        return ResponseEntity.ok().build();
    }
}
