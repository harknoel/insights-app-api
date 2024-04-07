package com.insights.blog.payload;

import com.insights.blog.entity.Comment;
import com.insights.blog.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogResponseDTO {
    private Integer blogId;

    private String title;

    private String content;

    private String date;

}
