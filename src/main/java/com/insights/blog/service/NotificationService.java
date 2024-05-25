package com.insights.blog.service;

import com.insights.blog.model.*;
import com.insights.blog.payload.NotificationResponseDTO;
import com.insights.blog.payload.UserDTO;
import com.insights.blog.repository.FollowRepository;
import com.insights.blog.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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

    public void notifyTargetUser(User currentUser, Blog blog, NotificationType notificationType) {
        User targetUser = blog.getUser();
        var notification = Notification.builder()
                .user(targetUser)
                .from(currentUser)
                .notificationType(notificationType)
                .isRead(Boolean.FALSE)
                .blog(blog)
                .build();
        notificationRepository.save(notification);
    }

    public int countNotifications(User currentUser) {
        return notificationRepository.findByUserAndIsReadFalse(currentUser).size();
    }

    public List<NotificationResponseDTO> allNotifications(User currentUser) {
        Optional<List<Notification>> optionalNotifications = notificationRepository.findByUser(currentUser);
        List<NotificationResponseDTO> notificationResponseDTOS = new ArrayList<>();

        if (optionalNotifications.isEmpty()) {
            return notificationResponseDTOS;
        }

        List<Notification> notifications = optionalNotifications.get();

        for (Notification notification : notifications) {
            Blog blog = notification.getBlog();
            Integer blogId = blog.getBlogId();
            String title = blog.getTitle();
            User author = notification.getFrom();
            LocalDateTime localDateTime = notification.getCreatedAt();
            UserDTO userDTO = UserDTO.builder()
                    .userId(author.getUserId())
                    .firstname(author.getFirstname())
                    .lastname(author.getLastname())
                    .build();

            NotificationResponseDTO notificationDTO = NotificationResponseDTO.builder()
                    .blogId(blogId)
                    .title(title)
                    .createdAt(localDateTime)
                    .notificationType(notification.getNotificationType())
                    .author(userDTO)
                    .build();

            notificationResponseDTOS.add(notificationDTO);
        }
        notificationResponseDTOS.sort(Comparator.comparing(NotificationResponseDTO::getCreatedAt).reversed());
        return notificationResponseDTOS;
    }

    public int readNotifications(User currentUser) {
        Optional<List<Notification>> optionalNotifications = notificationRepository.findByUser(currentUser);

        if (optionalNotifications.isEmpty()) {
            return -1;
        }

        List<Notification> notifications = optionalNotifications.get();

        for (Notification notification : notifications) {
            notification.setIsRead(Boolean.TRUE);
            notificationRepository.save(notification);
        }

        return 1;
    }
}
