package com.insights.blog.payload;

import com.insights.blog.model.Blog;
import com.insights.blog.model.NotificationType;
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
    private NotificationType notificationType;
    private UserDTO author;
}
