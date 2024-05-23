package com.insights.blog.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ImageNotFoundException extends RuntimeException{
    private final int blogId;

    public ImageNotFoundException(int blogId){
        super("Image with blogId" + blogId + "not found");
        this.blogId = blogId;
    }
}
