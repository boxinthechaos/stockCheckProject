package com.example.stock.service;

import com.example.stock.entity.UserEntity;
import com.example.stock.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileImageService {
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    @Transactional
    public void saveProfileImage(MultipartFile file, String username){
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        String storedFilePath = fileStorageService.storeFile(file);
        user.setProfileImageUrl(storedFilePath);
    }

    public String getProfileImageUrl(String username){
        return userRepository.findByUsername(username)
                .map(UserEntity::getProfileImageUrl)
                .orElse("/images/default-profile.png");
    }
}
