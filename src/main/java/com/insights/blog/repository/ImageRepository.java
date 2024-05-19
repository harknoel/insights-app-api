package com.insights.blog.repository;


import com.insights.blog.model.Blog;
import com.insights.blog.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findByBlog_BlogId(Integer blogId);

    List<Image> findImagesByBlog_BlogId(Integer blogId);

    void deleteImageByBlog_BlogId(Integer blogId);
}
