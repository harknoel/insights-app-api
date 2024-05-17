package com.insights.blog.service.cloud;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    public String uploadFile(MultipartFile file, String folderName);
}
