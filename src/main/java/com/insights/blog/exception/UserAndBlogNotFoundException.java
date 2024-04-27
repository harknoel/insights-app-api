package com.insights.blog.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserAndBlogNotFoundException extends RuntimeException {

    private final int userId;
    private final int blogId;

    public UserAndBlogNotFoundException(int userId, int blogId) {
        super("User with ID " + userId + "and Blog with ID " + blogId + "were not found");
        this.userId = userId;
        this.blogId = blogId;
    }
}
