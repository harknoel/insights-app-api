package com.insights.blog.payload;

import com.insights.blog.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {
    private Integer blogId;

    private String title;

    private String content;

    private String date;

    private List<Comment> comments;

    public PostResponseDTO(Integer blogId, String title, String content, String date) {
        this.blogId = blogId;
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
