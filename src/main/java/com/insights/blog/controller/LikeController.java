package com.insights.blog.controller;

import com.insights.blog.model.Blog;
import com.insights.blog.model.User;
import com.insights.blog.payload.LikeRequestDTO;
import com.insights.blog.security.CurrentUser;
import com.insights.blog.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@AllArgsConstructor
public class LikeController {

    private LikeService likeService;

    @PostMapping("/blog/{id}")
    public boolean like(@PathVariable int id, @CurrentUser User user) {
        return likeService.likeBlog(user, id);
    }
}
