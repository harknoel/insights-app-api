package com.insights.blog.service;

import com.insights.blog.entity.Blog;
import com.insights.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    BlogRepository blogRepository;

    public ResponseEntity<List<Blog>> getAllPosts() {
        return new ResponseEntity<>(blogRepository.findAll(), HttpStatus.OK);
    }
}
