package com.insights.blog.service;

import com.insights.blog.exception.UserNotFoundException;
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
        User targetUser = findUserById(userId);

        Follow follow = checkFollow(targetUser, currentUser);

        if(follow != null)
            return false;

        Follow newFollow = Follow.builder()
                .user(currentUser)
                .targetUser(targetUser)
                .build();
        followRepository.save(newFollow);

        return true;
    }

    public boolean unfollowUserById(Integer userId, User currentUser) {
        User targetUser = findUserById(userId);

        Follow follow = checkFollow(targetUser, currentUser);

        if(follow == null)
            return false;

        followRepository.delete(follow);

        return true;
}

    public User findUserById(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }

    public Follow checkFollow(User targetUser, User currentUser) {
        Optional<Follow> follow = followRepository.findByUserAndTargetUser(currentUser, targetUser);

        return follow.orElse(null);
    }
}
