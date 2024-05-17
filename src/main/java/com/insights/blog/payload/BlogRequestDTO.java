package com.insights.blog.payload;

import com.insights.blog.model.Image;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.util.List;

@Data
public class BlogRequestDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
