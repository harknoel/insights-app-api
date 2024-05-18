package com.insights.blog.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BlogRequestDTO {
    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
