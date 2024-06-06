package com.insights.blog.service;

import com.insights.blog.model.Blog;
import com.insights.blog.model.Like;
import com.insights.blog.model.NotificationType;
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
    private final NotificationService notificationService;

    public boolean userHasLikePost(User user, Blog blog) {
        return likeRepository.existsByUserAndBlog(user, blog);
    }

    public int toggleLikeBlog(User user, Integer blogId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow();

        if (!userHasLikePost(user, blog)) {
            var like = Like.builder()
                    .user(user)
                    .blog(blog)
                    .build();
            likeRepository.save(like);
            if (!user.getUserId().equals(blog.getUser().getUserId())) {
                notificationService.notifyTargetUser(user, blog, NotificationType.LIKE_POST);
            }
        } else {
            Like like = likeRepository.findByUserAndBlog(user, blog);
            likeRepository.delete(like);
        }
        return blog.getLikes().size();
    }

    public boolean checkUserLike(User user, int blogId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow();
        return userHasLikePost(user, blog);
    }
}