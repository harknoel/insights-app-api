package com.insights.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogResponseDTO {
    private Integer blogId;

    private String title;

    private Integer likes;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserDTO user;
}
