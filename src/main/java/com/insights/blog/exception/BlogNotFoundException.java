package com.insights.blog.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BlogNotFoundException extends RuntimeException {

    private final int blogId;

    public BlogNotFoundException(int blogId) {
        super("Blog with ID " + blogId + " not found");
        this.blogId = blogId;
    }
}
