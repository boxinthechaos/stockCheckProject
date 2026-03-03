package com.example.stock.controller;

import com.example.stock.entity.UserEntity;
import com.example.stock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.print.attribute.standard.Media;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final UserRepository repository;
    private final UserRepository userRepository;

    @GetMapping("/{fileName}")
    public ResponseEntity<?> saveProfileImage(@PathVariable String fileName){
        String decodedFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);

        Optional<UserEntity> useropt = userRepository.findByProfileImageUrl(decodedFileName);
        if(useropt.isEmpty() || useropt.get().getProfileImageData() == null){
            return ResponseEntity.notFound().build();
        }

        byte[] imageData = useropt.get().getProfileImageData();
        String contentType = "image/jpeg";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(new ByteArrayResource(imageData));
    }
}
