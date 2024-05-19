package com.insights.blog.service;

import com.insights.blog.model.Blog;
import com.insights.blog.model.Notification;
import com.insights.blog.model.NotificationType;
import com.insights.blog.model.User;
import com.insights.blog.repository.FollowRepository;
import com.insights.blog.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    @Autowired
    private final SocketIOService socketIOService;
    @Autowired
    private final NotificationRepository notificationRepository;
    @Autowired
    private FollowRepository followRepository;

    public void notifyFollowers(User publisher, Blog blog) {

        List<User> followers = followRepository.findUsersByTargetUser(publisher);

        for (User follower : followers) {
            Notification notification = Notification.builder()
                    .user(follower)
                    .from(publisher)
                    .notificationType(NotificationType.BLOG_POST)
                    .isRead(false)
                    .blog(blog)
                    .build();

            notificationRepository.save(notification);
            socketIOService.sendNotification("new_notification", notification, follower.getSocketId());
        }
    }
}
