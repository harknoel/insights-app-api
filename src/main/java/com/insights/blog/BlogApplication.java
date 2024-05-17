package com.insights.blog;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BlogApplication {
//	private static final Logger logger = LoggerFactory.getLogger(BlogApplication.class);
	public static void main(String[] args) {

//		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//				"cloud_name", "dbg6h9kts",
//				"api_key", "137643664114531",
//				"api_secret", "bvUPBYdAk2NtUkx6X3T4VqUP8fY",
//				"secure", true
//		));
//
//		SingletonManager manager = new SingletonManager();
//		manager.setCloudinary(cloudinary);
//		manager.init();
//		// Log a message indicating successful connection to Cloudinary
//		logger.info("Connected to Cloudinary successfully!");


		SpringApplication.run(BlogApplication.class, args);
	}


}
