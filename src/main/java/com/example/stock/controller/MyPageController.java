package com.example.stock.controller;

import com.example.stock.entity.UserEntity;
import com.example.stock.repository.UserRepository;
import com.example.stock.service.ProfileImageService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final UserRepository userRepository;

    @GetMapping("/mypage")
    public String myPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        Optional<UserEntity> userEntity = userRepository.findByUsername(currentUserName);

        if(userEntity.isPresent()){
            UserEntity user = userEntity.get();
            model.addAttribute("username", user.getUsername());
            model.addAttribute("useremail", user.getUserEmail());
            model.addAttribute("profileImageUrl", user.getProfileImageUrl());
        }
        return "mypage";
    }
}
