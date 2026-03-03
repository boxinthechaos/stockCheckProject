package com.example.stock.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String password;

    private String role;

    private String profileImageUrl;

    @Lob
    @Column(name = "profile_image_data")
    private byte[] profileImageData;

    @Column(name = "user_email")
    private String userEmail;
}
