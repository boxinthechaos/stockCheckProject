package com.example.stock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/loginP")
    public String loginP(){
        return "chart/login";
    }

    @GetMapping("/reset-password")
    public String restPasswordP(){
        return "chart/reset-password";
    }
}
