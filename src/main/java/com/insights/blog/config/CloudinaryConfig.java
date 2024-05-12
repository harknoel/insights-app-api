package com.insights.blog.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    private static final Logger logger = LoggerFactory.getLogger(CloudinaryConfig.class);

    @Bean
    public Cloudinary cloudinary() {
        logger.info("Initializing Cloudinary bean...");
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dbg6h9kts",
                "api_key", "137643664114531",
                "api_secret", "bvUPBYdAk2NtUkx6X3T4VqUP8fY"
        ));
        logger.info("Cloudinary bean initialized successfully");
        return cloudinary;
    }
}
