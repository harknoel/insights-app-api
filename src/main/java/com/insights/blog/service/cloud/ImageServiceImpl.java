package com.insights.blog.service.cloud;

import com.insights.blog.model.Blog;
import com.insights.blog.model.Image;
import com.insights.blog.payload.ImageModelDTO;
import com.insights.blog.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ImageRepository imageRepository;


    @Override
    public ResponseEntity<Map> uploadImage(ImageModelDTO imageModelDTO) {
        try{

            if(imageModelDTO.getImageFile().isEmpty()){
                return ResponseEntity.badRequest().build();
            }
            Image image = new Image();
//            image.setName(imageModelDTO.getName());
            image.setImageURL(cloudinaryService.uploadFile(imageModelDTO.getImageFile(), "blog_images"));
            if(image.getImageURL()==null){
                return ResponseEntity.badRequest().build();
            }
            imageRepository.save(image);
            return ResponseEntity.ok().body(Map.of("url", image.getImageURL()));
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Image> getImagesByBlogId(Integer blogId) {
        return imageRepository.findByBlog_BlogId(blogId);
    }

    @Override
    public List<Image> findImagesByBlogId(Integer blogId) {
        return imageRepository.findImagesByBlog_BlogId(blogId);
    }

    @Override
    public List<String> deleteImagesByBlogId(Integer blogId) {
        List<Image> imagesToDelete = imageRepository.findByBlog_BlogId(blogId);
        List<String> deletedImageURLs = imagesToDelete.stream()
                .map(Image::getImageURL)
                .collect(Collectors.toList());
        imageRepository.deleteAll(imagesToDelete);
        return deletedImageURLs;
    }
}
