package com.example.stock.service;

import com.example.stock.jwt.JwtUtil;
import com.example.stock.repository.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;

    public void logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();

        if(cookies != null){
            for(Cookie cookie : cookies){
                if("session_id".equals(cookie.getName())){
                    // session_id 쿠키 삭제
                    Cookie deleteCookie = new Cookie("session_id", null);
                    deleteCookie.setPath("/");
                    deleteCookie.setHttpOnly(true);
                    deleteCookie.setMaxAge(0);
                    response.addCookie(deleteCookie);
                    break;
                }
            }
        }

        SecurityContextHolder.clearContext();
    }
}
