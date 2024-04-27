package com.insights.blog.repository;

import com.insights.blog.model.Blog;
import com.insights.blog.model.Like;
import com.insights.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikeRepository extends JpaRepository<Like, Integer> {
    boolean existsByUserAndBlog(User user, Blog blog);
}
