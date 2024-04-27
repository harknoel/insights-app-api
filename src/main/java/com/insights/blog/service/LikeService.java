package com.insights.blog.service;

import com.insights.blog.exception.UserAndBlogNotFoundException;
import com.insights.blog.model.Blog;
import com.insights.blog.model.Like;
import com.insights.blog.model.User;
import com.insights.blog.repository.BlogRepository;
import com.insights.blog.repository.LikeRepository;
import com.insights.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;

    public boolean userHasLikePost(User user, Blog blog) {
        return likeRepository.existsByUserAndBlog(user, blog);
    }

    public boolean likeBlog(Integer userId, Integer blogId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Blog> blogOptional = blogRepository.findById(blogId);

        if (userOptional.isEmpty() && blogOptional.isEmpty()) {
            throw new UserAndBlogNotFoundException(userId, blogId);
        }

        User user = userOptional.get();
        Blog blog = blogOptional.get();

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