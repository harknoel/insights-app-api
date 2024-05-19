package com.insights.blog.service;

import com.insights.blog.model.Blog;
import com.insights.blog.model.Like;
import com.insights.blog.model.User;
import com.insights.blog.repository.BlogRepository;
import com.insights.blog.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final BlogRepository blogRepository;

    public boolean userHasLikePost(User user, Blog blog) {
        return likeRepository.existsByUserAndBlog(user, blog);
    }

    public boolean likeBlog(User user, Integer blogId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow();

        if (!userHasLikePost(user, blog)) {
            var like = Like.builder()
                    .user(user)
                    .blog(blog)
                    .build();
            likeRepository.save(like);
            return true;
        }
        return false;
    }
}