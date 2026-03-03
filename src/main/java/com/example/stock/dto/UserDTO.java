package com.example.stock.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private int id;
    private String username;
    private String role;

    public UserDTO(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
