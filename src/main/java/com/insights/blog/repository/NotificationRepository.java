package com.insights.blog.repository;

import com.insights.blog.model.Blog;
import com.insights.blog.model.Notification;
import com.insights.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserAndIsReadFalse(User user);
    Optional<List<Notification>> findByUser(User user);
    List<Notification> findNotificationsByBlog(Blog blog);
    List<Notification> getAllByBlog_BlogId(Integer blogId);
}
