package org.rmagallangonzalez.hotel_reservation_system.common;

import java.util.Date;

import javax.crypto.SecretKey;

import org.rmagallangonzalez.hotel_reservation_system.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {
    private final SecretKey secretKey;
    private final long experation;

    public JwtUtil(
        @Value("${jwt.secret-key}") String secret,
        @Value("${jwt.expiration}") long expiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.experation = expiration;
    }

    public String createToken(Authentication authentication) {
        Date now = new Date();

        return Jwts.builder()
            .subject(authentication.getName())
            .issuedAt(now)
            .expiration(new Date(now.getTime() + this.experation))
            .signWith(secretKey)
            .compact();
    }

    public Claims extractPayload(String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public String extractEmail(String token) {
        return this.extractPayload(token).getSubject();
    }

    public boolean isValidToken(String token, User user) {
        final String email = this.extractEmail(token);

        return (email.equals(user.getEmail())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return this.extractPayload(token).getExpiration().before(new Date());
    }
}
