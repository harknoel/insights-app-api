package com.insights.blog.exception;

public class BlogNotFoundException extends RuntimeException {

    private final int blogId;

    public BlogNotFoundException(int blogId) {
        super("Blog with ID " + blogId + " not found");
        this.blogId = blogId;
    }

    public int getBlogId() {
        return blogId;
    }
}
