package ru.example.edu.dto;

import lombok.Data;



@Data
public class AuthDto {
    private String accessToken;
    private String refreshToken;
}
