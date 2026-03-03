package com.example.stock.controller;

import com.example.stock.dto.JoinDTO;
import com.example.stock.service.EmailService;
import com.example.stock.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;
    private final EmailService emailService;

    @GetMapping("/join")
    public String joinP(){
        return "chart/join";
    }

    @PostMapping("/join")
    @ResponseBody
    public ResponseEntity<String> join(@RequestBody JoinDTO dto){
        boolean result = joinService.joinProcess(dto);

        if(result){
            return ResponseEntity.ok("회원가입 성공");
        }
        else{
            return ResponseEntity.badRequest().body("이미 존재하는 아이디 또는 이메일입니다");
        }
    }

    @PostMapping("/send-verification-code")
    @ResponseBody
    public ResponseEntity<String> sendVerificationCode(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            emailService.sendVerificationCode(email);
            return ResponseEntity.ok("인증 코드가 이메일로 전송되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("인증 코드 전송에 실패했습니다.");
        }
    }

    // [추가] 이메일 인증 코드 검증
    @PostMapping("/verify-code")
    @ResponseBody
    public ResponseEntity<String> verifyCode(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String code = payload.get("code");

        if (emailService.verifyCode(email, code)) {
            return ResponseEntity.ok("이메일 인증이 성공적으로 완료되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("인증 코드가 올바르지 않습니다.");
        }
    }
}
