package com.insights.blog.repository;

import com.insights.blog.model.Follow;
import com.insights.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    Optional<Follow> findByUserAndTargetUser(User currentUser, User targetUser);
    List<User> findUsersByTargetUser(User targetUser);
}
