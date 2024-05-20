package com.insights.blog.service;

import com.insights.blog.model.Blog;
import com.insights.blog.model.Image;
import com.insights.blog.model.NotificationType;
import com.insights.blog.model.User;
import com.insights.blog.exception.BlogNotFoundException;
import com.insights.blog.payload.*;
import com.insights.blog.repository.BlogRepository;
import com.insights.blog.repository.ImageRepository;
import com.insights.blog.service.cloud.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    @Autowired
    private final ImageRepository imageRepository;

    @Autowired
    private final CloudinaryService cloudinaryService;

    @Autowired
    private final NotificationService notificationService;

    public Page<BlogResponseDTO> getAllPosts(int page, String query) {
        // Define pagination parameters
        int pageSize = 5; // Number of posts per page
        Pageable pageable = PageRequest.of(page, pageSize);

        if (query.isEmpty()) {
            Page<Blog> blogPage = blogRepository.findAll(pageable);

            // Map Blog objects to PostResponseDTO objects
            return blogPage.map(this::buildPostResponseDTO);
        }
        Page<Blog> blogPage = blogRepository.findBlogsByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query, pageable);

        // Map Blog objects to PostResponseDTO objects
        return blogPage.map(this::buildPostResponseDTO);
    }


    private BlogResponseDTO buildPostResponseDTO(Blog blog) {
        UserDTO user = new UserDTO(blog.getUser().getUserId(), blog.getUser().getFirstname(), blog.getUser().getLastname());


        return BlogResponseDTO.builder()
                .blogId(blog.getBlogId())
                .title(blog.getTitle())
                .likes(blog.getLikes().size())
                .content(blog.getContent())
                .createdAt(blog.getCreatedAt())
                .updatedAt(blog.getUpdatedAt())
                .user(user)
                .images(blog.getImages())
                .build();
    }

    public BlogResponseDTO addBlog(BlogRequestDTO blogRequestDTO, User currentUser, ImageModelDTO imageModelDTO) {

        try {
            var blog = Blog
                    .builder()
                    .title(blogRequestDTO.getTitle())
                    .content(blogRequestDTO.getContent())
                    .user(currentUser)
                    .build();
            blogRepository.save(blog);
            // Handle image if provided
            if (imageModelDTO != null && imageModelDTO.getImageFile() != null) {
                String imageURL = cloudinaryService.uploadFile(imageModelDTO.getImageFile(), "blog_images");

                var image = Image
                        .builder()
                        .blog(blog)
                        .user(currentUser)
                        .imageURL(imageURL)
                        .build();
                imageRepository.save(image);
            }

            notificationService.notifyFollowers(currentUser, blog, NotificationType.BLOG_POST);

            UserDTO user = new UserDTO(blog.getUser().getUserId(), blog.getUser().getFirstname(), blog.getUser().getLastname());
            return new BlogResponseDTO(blog.getBlogId(), blog.getTitle(), 0, blog.getContent(), blog.getCreatedAt(), blog.getUpdatedAt(), user, blog.getImages());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add blog with image(s)");
        }
    }

    public boolean deleteBlog(Integer id) {
        try {
            Optional<Blog> optionalBlog = blogRepository.findById(id);
            if (optionalBlog.isEmpty()) {
                throw new BlogNotFoundException(id);
            }
            blogRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public BlogResponseDTO updateBlog(Integer id, BlogRequestDTO blogRequestDTO) {
        try {
            Optional<Blog> optionalBlog = blogRepository.findById(id);
            if (optionalBlog.isEmpty()) {
                throw new BlogNotFoundException(id);
            }
            Blog blog = optionalBlog.get();
            if (blogRequestDTO.getTitle() != null) {
                blog.setTitle(blogRequestDTO.getTitle());
            }
            if (blogRequestDTO.getContent() != null) {
                blog.setContent(blogRequestDTO.getContent());
            }
//            if (blogRequestDTO.getImageURLs() != null) {
////                blog.setContent(blogRequestDTO.getImageURLs());
//            }

            blogRepository.save(blog);
            UserDTO user = new UserDTO(blog.getUser().getUserId(), blog.getUser().getFirstname(), blog.getUser().getLastname());
            return new BlogResponseDTO(blog.getBlogId(), blog.getTitle(), blog.getLikes().size(), blog.getContent(), blog.getCreatedAt(), blog.getUpdatedAt(), user, blog.getImages());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete blog", e);
        }
    }

    public BlogResponseDTO getBlogById(Integer id) {
        Blog blog = blogRepository.findById(id).orElseThrow();
        UserWithEmailDTO user = new UserWithEmailDTO(blog.getUser().getUserId(), blog.getUser().getFirstname(), blog.getUser().getLastname(), blog.getUser().getEmail());
        return new BlogResponseDTO(blog.getBlogId(), blog.getTitle(), blog.getLikes().size(), blog.getContent(), blog.getCreatedAt(), blog.getUpdatedAt(), user, blog.getImages());
    }

    public Page<BlogResponseDTO> getBlogsByUser(int page, String query, User user) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);
        if (query.isEmpty()) {
            Page<Blog> blogPage = blogRepository.findBlogsByUser(user, pageable);
            return blogPage.map(this::buildPostResponseDTO);
        }
        Page<Blog> blogPage = blogRepository.findBlogsByUserAndTitleContainingIgnoreCaseOrContentContainingIgnoreCase(user, query, query, pageable);
        return blogPage.map(this::buildPostResponseDTO);
    }
}
