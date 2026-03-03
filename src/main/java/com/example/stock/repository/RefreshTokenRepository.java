package com.example.stock.repository;

import com.example.stock.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<UserEntity, Long> {
}
