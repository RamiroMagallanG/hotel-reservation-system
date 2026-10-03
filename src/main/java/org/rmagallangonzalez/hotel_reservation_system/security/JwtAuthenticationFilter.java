package org.rmagallangonzalez.hotel_reservation_system.security;

import java.io.IOException;

import org.rmagallangonzalez.hotel_reservation_system.common.JwtUtil;
import org.rmagallangonzalez.hotel_reservation_system.user.User;
import org.rmagallangonzalez.hotel_reservation_system.user.UserRepository;
import org.rmagallangonzalez.hotel_reservation_system.user.exception.EmailNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request, HttpServletResponse response, 
        FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        } 
        
        try {
            String jwtToken = authHeader.substring(7);
            String userEmail = this.jwtUtil.extractPayload(jwtToken).getSubject();

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = this.userRepository.findByEmail(userEmail).orElseThrow(
                    () -> new EmailNotFoundException("Email not found")
                );

                if (jwtUtil.isValidToken(jwtToken, user)) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        userEmail, null, user.getAuthorities()
                    );

                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        } catch (ExpiredJwtException ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Token expired");
            return;
        } catch (JwtException ex) {
            SecurityContextHolder.clearContext();
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
        }
        
        
        filterChain.doFilter(request, response);
    }
}
