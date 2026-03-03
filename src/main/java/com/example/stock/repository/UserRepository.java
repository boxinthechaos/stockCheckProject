package com.example.stock.repository;

import com.example.stock.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserEntity, Integer> {
    Boolean existsByUsername(String username);
    Boolean existsByUserEmail(String userEmail);

    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByProfileImageUrl(String profileImageUrl);
}
