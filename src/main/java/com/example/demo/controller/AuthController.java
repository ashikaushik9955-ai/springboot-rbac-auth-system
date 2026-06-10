package com.example.demo.controller;


import jakarta.validation.Valid;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    


    


    

    // Register API
    @PostMapping("/register")
    public String registerUser(
            @Valid
            @RequestBody RegisterRequestDTO request  
    ) {

        userService.registerUser(request);

        return "User Registered Successfully";
    }



    @PostMapping("/login")
    public String loginUser(
        @Valid
        @RequestBody LoginRequestDTO request
) {

    return userService.loginUser(request);
}





@GetMapping("/student")
public String studentApi() {
    return "Welcome Student 😄";
}

@GetMapping("/teacher")
public String teacherApi() {
    return "Welcome Teacher 😎";
}

@GetMapping("/admin")
public String adminApi() {
    return "Welcome Admin 😈";
}

@PutMapping("/admin/approve-teacher/{id}")
public String approveTeacher(
        @PathVariable Long id
) {

    userService.approveTeacher(id);

    return "Teacher Approved Successfully 😎";
}
}