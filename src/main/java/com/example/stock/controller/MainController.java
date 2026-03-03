package com.example.stock.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main")
    public String MainPage(HttpServletRequest request, Model model) {
        String sessionId = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println("쿠키 이름: " + cookie.getName() + ", 값: " + cookie.getValue()); // ✅ 디버깅용 로그
                if ("session_id".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }

        System.out.println("최종 sessionId 값: " + sessionId); // ✅ 실제 넘겨질 값 로그
        model.addAttribute("sessionId", sessionId);
        return "chart/main";}
}
