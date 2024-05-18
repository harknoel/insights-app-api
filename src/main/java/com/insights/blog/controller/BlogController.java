package com.insights.blog.controller;

import com.insights.blog.model.User;
import com.insights.blog.payload.BlogRequestDTO;
import com.insights.blog.payload.BlogResponseDTO;
import com.insights.blog.security.CurrentUser;
import com.insights.blog.service.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/all")
    public ResponseEntity<Page<BlogResponseDTO>> getAllPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String query) {
        Page<BlogResponseDTO> postPage = blogService.getAllPosts(page, query);
        return new ResponseEntity<>(postPage, HttpStatus.OK);
    }

    @GetMapping("/get/blog/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BlogResponseDTO> getBlog(@PathVariable Integer id) {
        return new ResponseEntity<>(blogService.getBlogById(id), HttpStatus.OK);
    }

    @GetMapping("/get/blog/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<BlogResponseDTO>> getBlog(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "") String query, @CurrentUser User currentUser) {
        Page<BlogResponseDTO> postPage = blogService.getBlogsByUser(page, query, currentUser);
        return new ResponseEntity<>(postPage, HttpStatus.OK);
    }

    @PostMapping("/create/blog")
    @PreAuthorize("hasRole('USER')")
    public BlogResponseDTO createBlog(@RequestBody BlogRequestDTO blogRequestDTO, @CurrentUser User currentUser) {
        return blogService.addBlog(blogRequestDTO, currentUser);
    }

    @DeleteMapping("/delete/blog/{id}")
    @PreAuthorize("hasRole('USER')")
    public boolean deleteBlog(@PathVariable Integer id) {
        return blogService.deleteBlog(id);
    }

    @PutMapping("/update/blog/{id}")
    @PreAuthorize("hasRole('USER')")
    public BlogResponseDTO updateBlog(@PathVariable Integer id, @RequestBody BlogRequestDTO blogRequestDTO) {
        return blogService.updateBlog(id, blogRequestDTO);
    }
}
