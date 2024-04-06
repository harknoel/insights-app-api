package com.insights.blog.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("Email already exists");
    }
}
