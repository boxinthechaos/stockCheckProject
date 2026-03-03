package com.example.stock.service;

import com.example.stock.dto.UserDTO;
import com.example.stock.entity.UserEntity;
import com.example.stock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDTO getUserInfo(String username){
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("사용자를 찾을 수 없습니다"));

        return new UserDTO(user.getId(), user.getUsername(), user.getRole());
    }
}
