package com.example.stock.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChartPageController {

    @GetMapping("/chart/technology")
    public String showTechnologyChartPage(HttpServletRequest request, Model model) {
        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("session_id".equals(cookie.getName())) { // 정확히 이 이름!
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        model.addAttribute("sessionId", sessionId);
        return "chart/technology_chart";
    }
    @GetMapping("/technology1")
    public String showTechnologyChartPage1() {
        return "chart/technology_chart";
    }
    @GetMapping("/chart/healthcare")
    public String showHealthcareChartPage(HttpServletRequest request, Model model) {
        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("session_id".equals(cookie.getName())) { // 정확히 이 이름!
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        model.addAttribute("sessionId", sessionId);
        return "chart/healthcare_chart";
    }
    @GetMapping("/chart/finance")
    public String showFinanceChartPage(HttpServletRequest request, Model model) {
        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("session_id".equals(cookie.getName())) { // 정확히 이 이름!
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        model.addAttribute("sessionId", sessionId);
        return "chart/finance_chart";
    }
    @GetMapping("/chart/energy")
    public String showEnergyChartPage(HttpServletRequest request, Model model) {
        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("session_id".equals(cookie.getName())) { // 정확히 이 이름!
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        model.addAttribute("sessionId", sessionId);
        return "chart/energy_chart";
    }
    @GetMapping("/chart/basic_materials")
    public String showBasicMaterialsChartPage(HttpServletRequest request, Model model) {
        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("session_id".equals(cookie.getName())) { // 정확히 이 이름!
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        model.addAttribute("sessionId", sessionId);
        return "chart/basic_materials_chart"; // ⬅ HTML 파일 경로에 맞게 수정
    }
    @GetMapping("/chart/search")
    public String showSearchChartPage(HttpServletRequest request, Model model) {
        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("session_id".equals(cookie.getName())) { // 정확히 이 이름!
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        model.addAttribute("sessionId", sessionId);
        return "chart/search_chart";
    }
}
