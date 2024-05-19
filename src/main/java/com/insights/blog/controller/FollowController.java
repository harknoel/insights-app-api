package com.insights.blog.controller;

import com.insights.blog.model.User;
import com.insights.blog.security.CurrentUser;
import com.insights.blog.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/follows")
@AllArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow/{userId}")
    public boolean followUserById(@PathVariable Integer userId, @CurrentUser User currentUser) {
        return followService.followUserById(userId, currentUser);
    }

    @PostMapping("/unfollow/{userId}")
    public boolean unfollowUserById(@PathVariable Integer userId, @CurrentUser User currentUser) {
        return followService.unfollowUserById(userId, currentUser);
    }
}
