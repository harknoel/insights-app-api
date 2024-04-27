package com.insights.blog.service;

import com.insights.blog.model.Blog;
import com.insights.blog.model.User;
import com.insights.blog.exception.BlogNotFoundException;
import com.insights.blog.exception.UnauthorizedActionException;
import com.insights.blog.payload.BlogRequestDTO;
import com.insights.blog.payload.BlogResponseDTO;
import com.insights.blog.payload.UserDTO;
import com.insights.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    public Page<BlogResponseDTO> getAllPosts(int page) {
        // Define pagination parameters
        int pageSize = 5; // Number of posts per page
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Blog> blogPage = blogRepository.findAll(pageable);

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
                .build();
    }

    public BlogResponseDTO addBlog(BlogRequestDTO blogRequestDTO, User currentUser) {
        var blog = Blog
                .builder()
                .title(blogRequestDTO.getTitle())
                .content(blogRequestDTO.getContent())
                .user(currentUser)
                .build();
        blogRepository.save(blog);
        UserDTO user = new UserDTO(blog.getUser().getUserId(), blog.getUser().getFirstname(), blog.getUser().getLastname());
        return new BlogResponseDTO(blog.getBlogId(), blog.getTitle(), blog.getLikes().size(), blog.getContent(), blog.getCreatedAt(), blog.getUpdatedAt(), user);
    }

    public boolean deleteBlog(Integer id, User currentUser) {
        try {
            Optional<Blog> optionalBlog = blogRepository.findById(id);

            if (optionalBlog.isEmpty()) {
                throw new BlogNotFoundException(id);
            }

            Blog blog = optionalBlog.get();

            int blogUserId = blog.getUser().getUserId();
            int currentUserId = currentUser.getUserId();

            if (blogUserId == currentUserId) {
                blogRepository.deleteById(id);
                return true;
            } else {
                throw new UnauthorizedActionException("You are not authorized to delete this blog");
            }
        } catch (Exception e) {
            return false;
        }
    }

    public BlogResponseDTO updateBlog(Integer id, BlogRequestDTO blogRequestDTO, User currentUser) {
        try {
            Optional<Blog> optionalBlog = blogRepository.findById(id);

            if (optionalBlog.isEmpty()) {
                throw new BlogNotFoundException(id);
            }

            Blog blog = optionalBlog.get();

            int blogUserId = blog.getUser().getUserId();
            int currentUserId = currentUser.getUserId();

            if (blogUserId == currentUserId) {
                if (blogRequestDTO.getTitle() != null) {
                    blog.setTitle(blogRequestDTO.getTitle());
                }
                if (blogRequestDTO.getContent() != null) {
                    blog.setContent(blogRequestDTO.getContent());
                }

                blogRepository.save(blog);

                UserDTO user = new UserDTO(blog.getUser().getUserId(), blog.getUser().getFirstname(), blog.getUser().getLastname());
                return new BlogResponseDTO(blog.getBlogId(), blog.getTitle(), blog.getLikes().size(), blog.getContent(), blog.getCreatedAt(), blog.getUpdatedAt(), user);
            } else {
                throw new UnauthorizedActionException("You are not authorized to delete this blog");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete blog", e);
        }
    }
}
