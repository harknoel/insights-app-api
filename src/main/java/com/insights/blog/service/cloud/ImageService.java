package com.insights.blog.service.cloud;

import com.insights.blog.payload.ImageModelDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ImageService {
    public ResponseEntity<Map> uploadImage(ImageModelDTO imageModelDTO);
}
