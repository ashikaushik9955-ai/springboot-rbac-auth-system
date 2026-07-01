package com.example.demo.service;

import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.LoginRequestDTO;

import com.example.demo.repository.RoleRepository;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtil jwtUtil;


    public void registerUser(RegisterRequestDTO request) {

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new RuntimeException("Email already exists!");
        }

        // Create User Entity Object
        User user = new User();

        // Set data from DTO → Entity
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        // Hash Password
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
            );



        // Role Logic
    String selectedRole = request.getSelectedRole();

    if (selectedRole.equalsIgnoreCase("STUDENT")) {

        Role studentRole = roleRepository
                .findByName("ROLE_STUDENT")
                .orElseThrow(() ->
                        new RuntimeException("Student role not found"));

        user.getRoles().add(studentRole);

        // Student verified by default
        user.setVerified(true);

    } else if (selectedRole.equalsIgnoreCase("TEACHER")) {

        Role teacherRole = roleRepository
                .findByName("ROLE_TEACHER")
                .orElseThrow(() ->
                        new RuntimeException("Teacher role not found"));

        user.getRoles().add(teacherRole);

        // Teacher needs approval
        user.setVerified(false);

    } else {
        throw new RuntimeException("Invalid role selected");
    }    

        // Save into Database
        userRepository.save(user);
    }


  public String loginUser(LoginRequestDTO request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    if (!passwordEncoder.matches(
            request.getPassword(),
            user.getPassword()
    )) {

        throw new RuntimeException("Wrong password");
    }


    // Teacher verification check
if (user.getRoles().stream()
        .anyMatch(role ->
                role.getName().equals("ROLE_TEACHER"))
        && !user.isVerified()) {

    return "Teacher account pending approval";
}

    
    //ROLE EXTRACTION - TODO: Future enhancement - Generate JWT with all user roles instead of first role only.
    
    String role = user.getRoles()
        .stream()
        .findFirst()
        .get()
        .getName();

        String token = jwtUtil.generateToken(
        user.getEmail(),
        role
);

return token;
}






public String approveTeacher(Long id) {

    User user = userRepository.findById(id)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    boolean isTeacher = user.getRoles()
            .stream()
            .anyMatch(role ->
                    role.getName().equals("ROLE_TEACHER"));

    if (!isTeacher) {
        throw new RuntimeException(
                "This user is not a teacher"
        );
    }

    user.setVerified(true);

    userRepository.save(user);

    return "Teacher Approved Successfully 😎"; 
}
    
}


