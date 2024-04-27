package com.insights.blog.controller;

import com.insights.blog.model.Blog;
import com.insights.blog.model.User;
import com.insights.blog.payload.PostRequestDTO;
import com.insights.blog.payload.PostResponseDTO;
import com.insights.blog.security.CurrentUser;
import com.insights.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("all/{page}")
    public ResponseEntity<List<Blog>> getAllPosts(@PathVariable int page) {
        Page<Blog> postPage = postService.getAllPosts(page);
        List<Blog> posts = postPage.getContent();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PostMapping("/create/blog")
    @PreAuthorize("hasRole('USER')")
    public PostResponseDTO createBlog(@RequestBody PostRequestDTO postRequestDTO, @CurrentUser User currentUser) {
        return postService.addBlog(postRequestDTO, currentUser);
    }

    @DeleteMapping("/delete/blog/{id}")
    @PreAuthorize("hasRole('USER')")
    public boolean deleteBlog(@PathVariable Integer id, @CurrentUser User currentUser) {
        return postService.deleteBlog(id, currentUser);
    }

    @PutMapping("/update/blog/{id}")
    @PreAuthorize("hasRole('USER')")
    public PostResponseDTO updateBlog(@PathVariable Integer id, @RequestBody PostRequestDTO postRequestDTO, @CurrentUser User currentUser) {
        return postService.updateBlog(id, postRequestDTO, currentUser);
    }
}
