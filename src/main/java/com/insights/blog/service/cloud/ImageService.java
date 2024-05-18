package com.insights.blog.service.cloud;

import com.insights.blog.model.Blog;
import com.insights.blog.model.Image;
import com.insights.blog.payload.ImageModelDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public interface ImageService {
    public ResponseEntity<Map> uploadImage(ImageModelDTO imageModelDTO);

    public List<Image> getImagesByBlogId(Integer blogId);

}
