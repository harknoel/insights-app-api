package com.insights.blog.controller;

import com.insights.blog.model.Notification;
import com.insights.blog.model.User;
import com.insights.blog.payload.NotificationResponseDTO;
import com.insights.blog.security.CurrentUser;
import com.insights.blog.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/count")
    public ResponseEntity<Integer> getNotificationCount(@CurrentUser User currentUser) {
        int count = notificationService.countNotifications(currentUser);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NotificationResponseDTO>> getAllNotifications(@CurrentUser User currentUser) {
        List<NotificationResponseDTO> notifications = notificationService.allNotifications(currentUser);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/read")
    public int readNotifications(@CurrentUser User currentUser) {
        return notificationService.readNotifications(currentUser);
    }
}
