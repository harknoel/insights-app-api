package com.insights.blog.controller;

import com.insights.blog.model.Notification;
import com.insights.blog.model.User;
import com.insights.blog.security.CurrentUser;
import com.insights.blog.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/notifications")
@PreAuthorize("hasRole('USER')")
public class NotificationController {

    private final BlockingQueue<String> updates = new LinkedBlockingQueue<>();
    private final NotificationService notificationService;

    @GetMapping("/updates")
    public ResponseEntity<String> getUpdates() throws InterruptedException {
        String update = updates.poll(60, TimeUnit.SECONDS); // Wait for up to 60 seconds
        if (update == null) {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
        }
        return ResponseEntity.ok(update);
    }

    // Method to add new updates to the queue
    public void addUpdate(String update) {
        if (!updates.offer(update)) {
            System.out.println("Queue is full, update dropped: " + update);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> updateData() {
        addUpdate("Notification added");
        return ResponseEntity.ok("Notification added");
    }
}
