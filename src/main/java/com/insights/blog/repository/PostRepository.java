package com.insights.blog.repository;

import com.insights.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Blog, Integer> {

}
