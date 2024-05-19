package com.insights.blog.controller;

import com.insights.blog.model.User;
import com.insights.blog.security.CurrentUser;
import com.insights.blog.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/following/{userId}")
    public boolean isUserFollowing(@PathVariable Integer userId,@CurrentUser User currentUser) {
        return followService.checkFollow(userId, currentUser) != null;
    }

    @PostMapping("/toggle/follow/{userId}")
    public void toggleFollow(@PathVariable Integer userId, @CurrentUser User currentUser) {
        followService.toggleFollow(userId, currentUser);
    }
}
