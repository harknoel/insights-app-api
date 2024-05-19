package com.insights.blog.controller;

import com.insights.blog.model.Blog;
import com.insights.blog.model.Image;
import com.insights.blog.payload.ImageModelDTO;
import com.insights.blog.repository.ImageRepository;
import com.insights.blog.service.cloud.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/get/{blogId}")
    public ResponseEntity<List<Image>> getImageByBlogId(@PathVariable Integer blogId){
        List<Image> imageList = imageService.getImagesByBlogId(blogId);
        return ResponseEntity.ok(imageList);
    }

//    @DeleteMapping("/delete/{blogId}")
//    public ResponseEntity<List<Image>> deleteImageByBlogId(@PathVariable Integer blogId){
//        imageService.deleteImagesByBlogId(blogId);
//        return ResponseEntity.ok(imageService.getImagesByBlogId(blogId));
//    }

    @DeleteMapping("/delete/{blogId}")
    public ResponseEntity<List<String>> deleteImageByBlogId(@PathVariable Integer blogId) {
        List<String> deletedImageURLs = imageService.deleteImagesByBlogId(blogId);
        return ResponseEntity.ok(deletedImageURLs);
    }
}
