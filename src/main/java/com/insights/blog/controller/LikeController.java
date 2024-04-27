package com.insights.blog.controller;

import com.insights.blog.model.Blog;
import com.insights.blog.model.User;
import com.insights.blog.payload.LikeRequestDTO;
import com.insights.blog.service.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/likes")
@AllArgsConstructor
public class LikeController {

    private LikeService likeService;

    @PostMapping("/blog")
    public boolean like(@RequestBody LikeRequestDTO likeRequestDTO) {
        return likeService.likeBlog(likeRequestDTO.getUserId(), likeRequestDTO.getBlogId());
    }
}
