package com.example.stock.controller;

import com.example.stock.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PasswordController {
    private final PasswordService passwordService;

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String username,
                                @RequestParam String newPassword) {
        boolean success = passwordService.restPassword(username, newPassword);
        return success ? "비밀번호 변경 완료" : "해당 유저가 존재하지 않습니다.";
    }

}
