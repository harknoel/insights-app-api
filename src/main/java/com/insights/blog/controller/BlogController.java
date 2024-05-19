package com.insights.blog.controller;

import com.insights.blog.model.Image;
import com.insights.blog.model.User;
import com.insights.blog.payload.BlogRequestDTO;
import com.insights.blog.payload.BlogResponseDTO;
import com.insights.blog.payload.ImageModelDTO;
import com.insights.blog.repository.ImageRepository;
import com.insights.blog.security.CurrentUser;
import com.insights.blog.service.BlogService;
import com.insights.blog.service.cloud.ImageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
public class BlogController {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

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
    public BlogResponseDTO createBlog(
            @RequestPart("blog") BlogRequestDTO blogRequestDTO,
            @CurrentUser User currentUser)
//            @RequestPart("image") MultipartFile imageFile)
    {

//        ImageModelDTO imageModelDTO = new ImageModelDTO();
//        imageModelDTO.setImageFile(imageFile);

//        // First upload the image
//        imageService.uploadImage(imageModelDTO);

        // Then add the blog
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
