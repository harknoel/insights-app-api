package com.insights.blog.service;

import com.insights.blog.entity.Blog;
import com.insights.blog.entity.User;
import com.insights.blog.payload.BlogRequestDTO;
import com.insights.blog.payload.BlogResponseDTO;
import com.insights.blog.repository.BlogRepository;
import com.insights.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;

    private final UserRepository userRepository;

    public ResponseEntity<List<Blog>> getAllPosts() {
        return new ResponseEntity<>(blogRepository.findAll(), HttpStatus.OK);
    }

    public BlogResponseDTO addBlog(BlogRequestDTO blogRequestDTO) {
        Optional<User> requestUser = userRepository.findByEmail(blogRequestDTO.getEmail());
        if(requestUser.isEmpty()) {
            System.out.println(blogRequestDTO.getEmail());
            // TO DO throw exception
            return null;
        }

        User user = requestUser.get();
        var blog = Blog
                .builder()
                .title(blogRequestDTO.getTitle())
                .content(blogRequestDTO.getContent())
                .user(user)
                .build();
        blogRepository.save(blog);

        return new BlogResponseDTO(blog.getBlogId(), blog.getTitle(), blog.getContent(), blog.getDate(), blog.getComments());
    }
}
