package com.insights.blog.service.cloud;

import com.insights.blog.model.Image;
import com.insights.blog.payload.ImageModelDTO;
import com.insights.blog.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

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
            image.setImageURL(cloudinaryService.uploadFile(imageModelDTO.getImageFile(), "folder_1"));
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

}
