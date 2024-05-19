package com.insights.blog.service;

import com.insights.blog.model.*;
import com.insights.blog.repository.FollowRepository;
import com.insights.blog.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class NotificationService {
    private final FollowRepository followRepository;
    private final NotificationRepository notificationRepository;

    public void notifyFollowers(User currentUser, Blog blog, NotificationType notificationType) {
        Optional<List<Follow>> optionalFollows = followRepository.findUsersByTargetUser(currentUser);

        if (optionalFollows.isEmpty()) {
            return;
        }

        List<Follow> follows = optionalFollows.get();

        for (Follow follower : follows) {
            User targetUser = follower.getUser();
            var notification = Notification.builder()
                    .user(targetUser)
                    .from(currentUser)
                    .notificationType(notificationType)
                    .isRead(Boolean.FALSE)
                    .blog(blog)
                    .build();

            notificationRepository.save(notification);
        }
    }

    public int countNotifications(User currentUser) {
        return notificationRepository.findByUserAndIsReadFalse(currentUser).size();
    }
}
