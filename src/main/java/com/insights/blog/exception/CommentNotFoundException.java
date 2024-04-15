package com.insights.blog.exception;

public class CommentNotFoundException extends RuntimeException {

    private final int commentId;

    public CommentNotFoundException(int commentId) {
        super("Comment with ID " + commentId + " not found");
        this.commentId = commentId;
    }

    public int getCommentId() {
        return commentId;
    }
}

