package com.insights.blog.service;

import com.insights.blog.entity.Blog;
import com.insights.blog.entity.User;
import com.insights.blog.payload.PostRequestDTO;
import com.insights.blog.payload.PostResponseDTO;
import com.insights.blog.repository.PostRepository;
import com.insights.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    public ResponseEntity<List<Blog>> getAllPosts() {
        return new ResponseEntity<>(postRepository.findAll(), HttpStatus.OK);
    }

    public PostResponseDTO addBlog(PostRequestDTO postRequestDTO, User currentUser) {
        var blog = Blog
                .builder()
                .title(postRequestDTO.getTitle())
                .content(postRequestDTO.getContent())
                .user(currentUser)
                .date(postRequestDTO.getDate())
                .build();
        postRepository.save(blog);
        return new PostResponseDTO(blog.getBlogId(), blog.getTitle(), blog.getContent(), blog.getDate());
    }

    public boolean deleteBlog(Integer id) {
        return false;
    }
}
