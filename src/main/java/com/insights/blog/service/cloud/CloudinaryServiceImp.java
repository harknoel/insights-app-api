package com.insights.blog.service.cloud;

import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import io.jsonwebtoken.lang.Maps;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.cloudinary.Cloudinary;

@Service
public class CloudinaryServiceImp implements CloudinaryService {

    @Resource
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file, String folderName) {
        try {
            HashMap<Object, Object> options = new HashMap<>();
            options.put("folder", folderName);
            Map uploadFile = cloudinary.uploader().upload(file.getBytes(), options);
            String publicId = (String) uploadFile.get("public_id");
            return cloudinary.url().secure(true).generate(publicId);
        }
        catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public ApiResponse deleteResources(List<String> publicIds) throws Exception {
        return cloudinary.api().deleteResources(publicIds, ObjectUtils.emptyMap());

    }

}
