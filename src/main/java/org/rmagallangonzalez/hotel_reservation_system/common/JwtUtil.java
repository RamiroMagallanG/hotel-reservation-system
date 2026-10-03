package org.rmagallangonzalez.hotel_reservation_system.common;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.rmagallangonzalez.hotel_reservation_system.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtUtil {
    private final SecretKey secretKey;
    private final long expiration;

    public JwtUtil(
        @Value("${jwt.secret-key}") String secret,
        @Value("${jwt.expiration}") long expiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expiration = expiration;
    }

    public String createToken(Authentication authentication) {
        Date now = new Date();
        List<String> roleList = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return Jwts.builder()
            .subject(authentication.getName())
            .claim("roles", roleList)
            .issuedAt(now)
            .expiration(new Date(now.getTime() + this.expiration))
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

    public boolean isValidToken(String token, User user) {
        if (token == null || user == null) {
            return false;
        }

        try {
            Claims claim = this.extractPayload(token);
            String email = claim.getSubject();
            Date expiration = claim.getExpiration();

            return (email.equals(user.getEmail())) && expiration.before(new Date());
        } catch (JwtException ex) {
            return false;
        }
    }
}
