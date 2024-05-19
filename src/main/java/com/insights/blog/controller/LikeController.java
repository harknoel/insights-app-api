package com.insights.blog.controller;

import com.insights.blog.model.User;
import com.insights.blog.security.CurrentUser;
import com.insights.blog.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/blog/{id}")
    public int like(@PathVariable int id, @CurrentUser User user) {
        return likeService.toggleLikeBlog(user, id);
    }

    @GetMapping("/user/blog/{id}")
    public boolean checkUserLike(@PathVariable int id, @CurrentUser User user) {
        return likeService.checkUserLike(user, id);
    }
}
