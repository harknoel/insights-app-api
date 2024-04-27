package com.insights.blog.service;

import com.insights.blog.model.Blog;
import com.insights.blog.model.User;
import com.insights.blog.exception.BlogNotFoundException;
import com.insights.blog.exception.UnauthorizedActionException;
import com.insights.blog.payload.PostRequestDTO;
import com.insights.blog.payload.PostResponseDTO;
import com.insights.blog.payload.UserDTO;
import com.insights.blog.repository.PostRepository;
import com.insights.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<Blog> getAllPosts(int page) {
        // Define pagination parameters
        int pageSize = 5; // Number of posts per page
        Pageable pageable = PageRequest.of(page, pageSize);

        // Retrieve the page of posts from the repository
        return postRepository.findAll(pageable);
    }

    public PostResponseDTO addBlog(PostRequestDTO postRequestDTO, User currentUser) {
        var blog = Blog
                .builder()
                .title(postRequestDTO.getTitle())
                .content(postRequestDTO.getContent())
                .user(currentUser)
                .build();
        postRepository.save(blog);
        UserDTO user = new UserDTO(blog.getUser().getUserId(), blog.getUser().getFirstname(), blog.getUser().getLastname());
        return new PostResponseDTO(blog.getBlogId(), blog.getTitle(), blog.getContent(), blog.getCreatedAt(), blog.getUpdatedAt(), user);
    }

    public boolean deleteBlog(Integer id, User currentUser) {
        try {
            Optional<Blog> optionalBlog = postRepository.findById(id);

            if (optionalBlog.isEmpty()) {
                throw new BlogNotFoundException(id);
            }

            Blog blog = optionalBlog.get();

            int blogUserId = blog.getUser().getUserId();
            int currentUserId = currentUser.getUserId();

            if (blogUserId == currentUserId) {
                postRepository.deleteById(id);
                return true;
            } else {
                throw new UnauthorizedActionException("You are not authorized to delete this blog");
            }
        } catch (Exception e) {
            return false;
        }
    }

    public PostResponseDTO updateBlog(Integer id, PostRequestDTO postRequestDTO, User currentUser) {
        try {
            Optional<Blog> optionalBlog = postRepository.findById(id);

            if (optionalBlog.isEmpty()) {
                throw new BlogNotFoundException(id);
            }

            Blog blog = optionalBlog.get();

            int blogUserId = blog.getUser().getUserId();
            int currentUserId = currentUser.getUserId();

            if (blogUserId == currentUserId) {
                if (postRequestDTO.getTitle() != null) {
                    blog.setTitle(postRequestDTO.getTitle());
                }
                if (postRequestDTO.getContent() != null) {
                    blog.setContent(postRequestDTO.getContent());
                }

                postRepository.save(blog);

                UserDTO user = new UserDTO(blog.getUser().getUserId(), blog.getUser().getFirstname(), blog.getUser().getLastname());
                return new PostResponseDTO(blog.getBlogId(), blog.getTitle(), blog.getContent(), blog.getCreatedAt(), blog.getUpdatedAt(), user);
            } else {
                throw new UnauthorizedActionException("You are not authorized to delete this blog");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete blog", e);
        }
    }
}
