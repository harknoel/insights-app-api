package com.insights.blog.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentNotFoundException extends RuntimeException {

    private final int commentId;

    public CommentNotFoundException(int commentId) {
        super("Comment with ID " + commentId + " not found");
        this.commentId = commentId;
    }

}

