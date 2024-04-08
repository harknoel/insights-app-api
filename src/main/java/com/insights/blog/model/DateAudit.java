package com.insights.blog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "date")
public class DateAudit {
    @Id
    @GeneratedValue
    private Integer id;

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date createdAt;

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private Date updatedAt;
}
