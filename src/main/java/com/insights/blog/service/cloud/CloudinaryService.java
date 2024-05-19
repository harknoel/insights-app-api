package com.insights.blog.service.cloud;

import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import io.jsonwebtoken.lang.Maps;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CloudinaryService {
    public String uploadFile(MultipartFile file, String folderName);


    public ApiResponse deleteResources(List<String> publicIds) throws Exception;
}
