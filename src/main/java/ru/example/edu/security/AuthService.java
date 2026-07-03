package ru.example.edu.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.example.edu.dto.AuthDto;
import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;



@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger LOGGER = LogManager.getLogger(AuthService.class);
    @Value("${jwt.secret}")
    private String jwtSecret;

    public AuthDto generateAuthToken(String username) {
        AuthDto authDto = new AuthDto();
        authDto.setAccessToken(generateJwtToken(username));
        authDto.setRefreshToken(generateRefreshToken(username));
        return authDto;
    }

    public AuthDto refreshBaseToken(String username, String refreshToken) {
        AuthDto authDto = new AuthDto();
        authDto.setAccessToken(generateJwtToken(username));
        authDto.setRefreshToken(refreshToken);
        return authDto;
    }

    public String getUserNameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public Boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JwtException", e);
            return false;
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("UnsupportedJwtException", ex);
            return false;
        } catch (MalformedJwtException e) {
            LOGGER.error("MalformedJwtException", e);
            return false;
        }catch (SecurityException e){
            LOGGER.error("SecurityException", e);
            return false;
        } catch(Exception e) {
            LOGGER.error("Invalid token", e);
            return false;
        }
    }

    private String generateJwtToken(String username) {
        Date date  = Date.from(LocalDateTime.now().plusMinutes(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(username)
                .expiration(date)
                .signWith(getSignKey())
                .compact();
    }

    private String generateRefreshToken(String username) {
        Date date  = Date.from(LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .subject(username)
                .expiration(date)
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
