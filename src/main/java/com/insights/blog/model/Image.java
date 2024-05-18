package com.insights.blog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
@EntityListeners(AuditingEntityListener.class)
public class Image {
//

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID imageId;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    @JsonBackReference
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;


    @Column(name = "url_image")
    private String imageURL;


}
