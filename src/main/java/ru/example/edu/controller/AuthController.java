package ru.example.edu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.example.edu.dto.*;
import ru.example.edu.service.PersonService;
import ru.example.edu.security.AuthService;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final PersonService personService;

    @PostMapping("/sign-in")
    public ResponseEntity<AuthDto> signIn(@RequestBody UserCredentialsDto dto) {
        try {
            AuthDto authDto = personService.singIn(dto);
            return ResponseEntity.ok(authDto);
        } catch (AuthenticationException e) {
            throw  new RuntimeException("Authentication failed " + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthDto> refresh(@RequestBody RefreshTokenDto dto) throws Exception {
        return ResponseEntity.ok(personService.refreshToken(dto));
    }

}
