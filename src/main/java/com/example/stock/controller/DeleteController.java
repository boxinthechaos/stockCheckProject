package com.example.stock.controller;

import com.example.stock.entity.UserEntity;
import com.example.stock.repository.UserRepository;
import com.example.stock.service.DeleteService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class DeleteController {

    private final DeleteService deleteService;
    private final UserRepository userRepository;

    @PostMapping("/delete")
    public ResponseEntity<String> delete(@AuthenticationPrincipal UserDetails userDetails, HttpServletResponse response) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        String username = userDetails.getUsername();
        UserEntity user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("유저 정보 없음");
        }

        deleteService.delete(user.getId());

        // --- 추가된 부분: JWT 쿠키 삭제 ---
        Cookie jwtCookie = new Cookie("session_id", null); // 값은 null로 설정
        jwtCookie.setPath("/");
        jwtCookie.setHttpOnly(true);
        jwtCookie.setMaxAge(0); // 즉시 만료 (삭제)
        response.addCookie(jwtCookie);
        // --- 추가된 부분 끝 ---

        return ResponseEntity.ok("탈퇴 완료");
    }
}
