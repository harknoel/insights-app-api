package com.insights.blog.service.cloud;

import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import com.insights.blog.model.Blog;
import com.insights.blog.model.Image;
import com.insights.blog.payload.ImageModelDTO;
import com.insights.blog.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
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
    @Autowired
    private CloudinaryServiceImp cloudinaryServiceImp;


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

        System.out.println("Image URLs to delete: " + deletedImageURLs);

        // Extract public IDs from URLs for Cloudinary deletion
        List<String> publicIds = deletedImageURLs.stream()
                .map(this::extractPublicId)
                .filter(StringUtils::hasText)
                .collect(Collectors.toList());

    // Debug statement to inspect the extracted public IDs
        System.out.println("Public IDs to delete: " + publicIds);

        if (publicIds.isEmpty()) {
            System.out.println("No valid public IDs found to delete.");
            return deletedImageURLs; // Or handle the case as needed
        }
        // Delete images from Cloudinary
        try{
            ApiResponse apiResponse = cloudinaryService.deleteResources(publicIds);
            System.out.println(apiResponse);
        }catch (IOException e){
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Delete images from the database
        imageRepository.deleteAll(imagesToDelete);

        return deletedImageURLs;
    }

    private String extractPublicId(String url) {
        try {
            System.out.println("Extracting public ID from URL: " + url); // Debug statement

            // Check if URL is valid and non-empty
            if (url == null || url.isEmpty()) {
                System.out.println("URL is null or empty");
                return null;
            }

            // Extract the part after 'upload/' to the end
            int uploadIndex = url.indexOf("upload/");
            if (uploadIndex == -1) {
                System.out.println("No valid public ID found in URL: " + url);
                return null;
            }

            String publicIdWithExtension = url.substring(uploadIndex + 7); // Adjusting for 'upload/'

            // Remove any file extension if present (e.g., .jpg, .png)
            int dotIndex = publicIdWithExtension.lastIndexOf('.');
            String publicId = (dotIndex == -1) ? publicIdWithExtension : publicIdWithExtension.substring(0, dotIndex);

            // Ensure to remove version prefix if present
            if (publicId.contains("v1/")) {
                publicId = publicId.substring(publicId.indexOf("v1/") + 3);
            }
            System.out.println("Extracted public ID: " + publicId); // Debug statement
            return publicId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
