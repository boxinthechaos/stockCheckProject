package com.example.stock.service;

import com.example.stock.dto.JoinDTO;
import com.example.stock.entity.UserEntity;
import com.example.stock.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();

        if(userRepository.existsByUsername(username) || userRepository.existsByUserEmail(email)){
            return false;
        }
        UserEntity data = new UserEntity();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setUserEmail(email);
        data.setRole("ROLE_USER");
        userRepository.save(data);

        return true;
    }
}
