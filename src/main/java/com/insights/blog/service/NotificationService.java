package com.insights.blog.service;

import com.insights.blog.model.Notification;
import com.insights.blog.model.User;
import com.insights.blog.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
   private final NotificationRepository notificationRepository;

    public void addUserNotification(Notification notification, User user) {
        List<Notification> notifications = user.getNotifications();

        notification.setUser(user);
        notifications.add(notification);
        notificationRepository.saveAll(notifications);
    }
}
