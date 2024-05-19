package com.insights.blog.payload;

import com.insights.blog.model.Blog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotificationResponseDTO {
    private Integer blogId;
    private String title;
    private LocalDateTime createdAt;
    private UserDTO author;
}
