package com.insights.blog.exception;

public class UserNotFoundException extends RuntimeException {

    private final int userId;

    public UserNotFoundException (int userId) {
        super("User with ID " + userId + " not found");
        this.userId = userId;
    }
}
