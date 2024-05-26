package com.insights.blog.service;

import com.insights.blog.model.Follow;
import com.insights.blog.model.NotificationType;
import com.insights.blog.model.User;
import com.insights.blog.repository.FollowRepository;
import com.insights.blog.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    private final UserRepository userRepository;

    private NotificationService notificationService;

    public boolean followUserById(Integer userId, User currentUser) {
        User targetUser = userRepository.findById(userId).orElseThrow();

        Follow newFollow = Follow.builder()
                .user(currentUser)
                .targetUser(targetUser)
                .build();
        notificationService.notifyFollowTargetUser(currentUser,targetUser, NotificationType.FOLLOW_USER);
        followRepository.save(newFollow);

        return true;
    }

    public boolean unfollowUserById(Integer userId, User currentUser) {
        Follow follow = checkFollow(userId, currentUser);
        followRepository.delete(follow);
        return true;
    }

    public User findUserById(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    public Follow checkFollow(int targetUserId, User currentUser) {
        User targetUser = findUserById(targetUserId);
        return followRepository.findByUserAndTargetUser(currentUser, targetUser).orElse(null);
    }

    public void toggleFollow(Integer userId, User currentUser) {
        if (userId.equals(currentUser.getUserId())) {
            return;
        }
        Follow follow = checkFollow(userId, currentUser);
        if (follow == null) {
            followUserById(userId, currentUser);
        } else {
            unfollowUserById(userId, currentUser);
        }
    }
}
