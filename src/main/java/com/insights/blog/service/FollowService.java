package com.insights.blog.service;

import com.insights.blog.model.Follow;
import com.insights.blog.model.User;
import com.insights.blog.repository.FollowRepository;
import com.insights.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    @Autowired
    private final FollowRepository followRepository;

    @Autowired
    private final UserRepository userRepository;

    public boolean followUserById(Integer userId, User currentUser) {
        User targetUser = userRepository.findById(userId).orElseThrow();

        Follow newFollow = Follow.builder()
                .user(currentUser)
                .targetUser(targetUser)
                .build();
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
