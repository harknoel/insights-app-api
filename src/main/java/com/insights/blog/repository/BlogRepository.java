package com.insights.blog.repository;

import com.insights.blog.model.Blog;
import com.insights.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Page<Blog> findBlogsByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content, Pageable pageable);
    Page<Blog> findBlogsByUser(User user, Pageable pageable);
    Page<Blog> findBlogsByUserAndTitleContainingIgnoreCaseOrContentContainingIgnoreCase(User user, String title, String content, Pageable pageable);
}
