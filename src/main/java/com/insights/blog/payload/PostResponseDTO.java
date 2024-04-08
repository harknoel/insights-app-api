package com.insights.blog.payload;

import com.insights.blog.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {
    private Integer blogId;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public PostResponseDTO(Integer blogId, String title, String content, LocalDateTime createdAt) {
        this.blogId = blogId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}
