package com.insights.blog.payload;

import com.insights.blog.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BlogRequestDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String date;
}
