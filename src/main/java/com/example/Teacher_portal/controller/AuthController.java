package com.example.Teacher_portal.controller;

import com.example.Teacher_portal.Entity.Role;
import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.exception.UserException;
import com.example.Teacher_portal.jwt.JwtProvider;
import com.example.Teacher_portal.repository.UserRepository;
import com.example.Teacher_portal.request.LoginRequest;
import com.example.Teacher_portal.response.AuthResponse;
import com.example.Teacher_portal.service.AuthService;
import com.example.Teacher_portal.service.impl.CustomUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/home")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserServiceImpl customUserService;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> registerStudentHandler(@RequestBody User user) throws UserException {
        AuthResponse authResponse = authService.registerStudent(user);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signup/teacher")
    public ResponseEntity<AuthResponse> registerTeacherHandler(@RequestBody User user) throws UserException {
        AuthResponse authResponse = authService.registerTeacher(user);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {
        // Delegate to the service layer
        AuthResponse authResponse = authService.loginUser(loginRequest);

        // Return response
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

}
