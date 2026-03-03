package com.example.stock.controller;

import com.example.stock.service.ProfileImageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileImageService profileImageService;

    @PostMapping("/upload")
    public void uploadProfilePicture(
            @RequestParam("profilePicture") MultipartFile file,
            Principal principal,
            HttpServletResponse response) throws IOException {

        profileImageService.saveProfileImage(file,principal.getName());
        response.sendRedirect("/mypage");


    }
}
