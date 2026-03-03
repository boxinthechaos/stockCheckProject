package com.example.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final ConcurrentHashMap<String, String> verificationCode = new ConcurrentHashMap<>();

    public String generateVerificationCode(){
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void sendVerificationCode(String toEmail){
        String code = generateVerificationCode();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("[PolMarket] 회원가입 이메일 인증 코드입니다.");
        message.setText("인증코드 : " + code);
        message.setFrom("pjsleey12312@gmail.com");

        javaMailSender.send(message);

        verificationCode.put(toEmail, code);
    }

    public boolean verifyCode(String email, String code){
        String storedCode = verificationCode.get(email);
        if(storedCode != null && storedCode.equals(code)){
            verificationCode.remove(email);
            return true;
        }
        return false;
    }
}
