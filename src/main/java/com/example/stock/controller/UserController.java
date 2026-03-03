package com.example.stock.controller;

import com.example.stock.dto.UserDTO;
import com.example.stock.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public UserDTO getUserInfo(Authentication authentication) {
        String username = authentication.getName();
        return userService.getUserInfo(username);
    }
}
