package com.insights.blog.repository;

import com.insights.blog.model.Notification;
import com.insights.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserAndIsReadFalse(User user);
}
