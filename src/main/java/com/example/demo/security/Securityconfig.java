package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class Securityconfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        
        // Public APIs
        .requestMatchers(
                "/auth/register",
                "/auth/login",
                "/auth/test",
                // Swagger
               "/swagger-ui/**",
               "/v3/api-docs/**"
        ).permitAll()

        // Student API
        .requestMatchers("/auth/student")
        .hasAuthority("ROLE_STUDENT")

        // Teacher API
        .requestMatchers("/auth/teacher")
        .hasAuthority("ROLE_TEACHER")

        // Admin API
        .requestMatchers("/auth/admin/**")
        .hasAuthority("ROLE_ADMIN")
        
        

        // Everything else protected
        .anyRequest().authenticated()
)


                // Stateless because JWT
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // Add JWT Filter
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}