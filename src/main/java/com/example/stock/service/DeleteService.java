package com.example.stock.service;

import com.example.stock.entity.UserEntity;
import com.example.stock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
@RequiredArgsConstructor
public class DeleteService {
    private final UserRepository userRepository;

    public void delete(int memberId){
        UserEntity user = userRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없음"));
        userRepository.delete(user);
    }
}
