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
public class CommentResponseDTO {

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserWithEmailDTO user;
}
