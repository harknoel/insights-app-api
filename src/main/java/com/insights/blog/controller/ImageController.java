package com.insights.blog.controller;

import com.insights.blog.payload.ImageModelDTO;
import com.insights.blog.repository.ImageRepository;
import com.insights.blog.service.cloud.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/images")
@AllArgsConstructor
public class ImageController {
    @Autowired
    private ImageRepository imageRepo;

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<Map> upload(ImageModelDTO imageModelDTO){
        try{
            return imageService.uploadImage(imageModelDTO);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
