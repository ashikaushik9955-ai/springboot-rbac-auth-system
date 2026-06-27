package com.example.demo.security;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;

//@Component
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Constructor Injection
    public RoleSeeder(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        // STUDENT ROLE
        if (roleRepository.findByName("ROLE_STUDENT").isEmpty()) {

            Role studentRole = new Role();
            studentRole.setName("ROLE_STUDENT");

            roleRepository.save(studentRole);
        }

        // TEACHER ROLE
        if (roleRepository.findByName("ROLE_TEACHER").isEmpty()) {

            Role teacherRole = new Role();
            teacherRole.setName("ROLE_TEACHER");

            roleRepository.save(teacherRole);
        }

        // ADMIN ROLE
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {

            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");

            roleRepository.save(adminRole);
        }

        
        // CREATE ADMIN USER
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {

            User admin = new User();

            admin.setName("Admin");
            admin.setEmail("admin@gmail.com");

            // password = admin123
            admin.setPassword(
                    passwordEncoder.encode("admin123")
            );

            admin.setVerified(true);

            Role adminRole = roleRepository
                    .findByName("ROLE_ADMIN")
                    .orElseThrow(() ->
                            new RuntimeException("Admin role not found"));

            admin.getRoles().add(adminRole);

            userRepository.save(admin);

           
        }
    }
}