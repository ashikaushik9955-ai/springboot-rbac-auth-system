package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    // Skip JWT filter for public APIs
    @Override
    protected boolean shouldNotFilter(
            HttpServletRequest request
    ) {

        String path = request.getServletPath();

        return path.equals("/auth/login")
                || path.equals("/auth/register");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // Get Authorization Header
        String authHeader =
                request.getHeader("Authorization");

        // Check if token exists
        if (authHeader != null
                && authHeader.startsWith("Bearer ")) {

            // Remove "Bearer "
            String token =
                    authHeader.substring(7);

            // Validate Token
            if (jwtUtil.validateToken(token)) {

                // Extract Email
                String email =
                        jwtUtil.extractEmail(token);

                // Extract Role
                String role =
                        jwtUtil.extractRole(token);

                      

                // Create Authentication Object
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(
                                        new SimpleGrantedAuthority(role)
                                )
                        );

                // Set authentication in Security Context
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        }

        // Continue Request
        filterChain.doFilter(request, response);
    }
}