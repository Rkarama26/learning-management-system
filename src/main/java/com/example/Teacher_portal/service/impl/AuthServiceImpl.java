package com.example.Teacher_portal.service.impl;

import com.example.Teacher_portal.Entity.Role;
import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.exception.UserException;
import com.example.Teacher_portal.jwt.JwtProvider;
import com.example.Teacher_portal.repository.UserRepository;
import com.example.Teacher_portal.request.LoginRequest;
import com.example.Teacher_portal.response.AuthResponse;
import com.example.Teacher_portal.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private CustomUserServiceImpl customUserService;

    @Override
    public AuthResponse registerStudent(User user) throws UserException {

        //Optional<User> userId = userRepository.findById(user.getId());
        Optional<User> email = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));

        // Check if email already exists
        if (email.isPresent()) {
            throw new UserException("User Already Exists");
        }

        String encoded_password = passwordEncoder.encode(user.getPassword());
        User newUser = new User();
        newUser.setPassword(encoded_password);
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setRole(Role.STUDENT);

        User savedUser = userRepository.save(newUser);

        // Generate authentication token
        Authentication auth = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtProvider.generateToken(auth);

        return new AuthResponse(token, "S Signup success");
    }

    @Override
    public AuthResponse registerTeacher(User user) throws UserException {
        // Optional<User> userId = userRepository.findById(user.getId());
        Optional<User> email = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));

        // Check if email already exists
        if (email.isPresent()) {
            throw new UserException("User Already Exists");
        }

        String encoded_password = passwordEncoder.encode(user.getPassword());
        User newUser = new User();
        newUser.setPassword(encoded_password);
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setRole(Role.TEACHER);

        User savedUser = userRepository.save(newUser);

        // Generate authentication token
        Authentication auth = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtProvider.generateToken(auth);

        return new AuthResponse(token, "T Signup success");
    }

    @Override
    public AuthResponse loginUser(LoginRequest loginRequest) {
        // Authenticate user

        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication auth = authenticate(username, password);
        // Set authentication in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Generate JWT token
        String token = jwtProvider.generateToken(auth);

        // Return response
        return new AuthResponse(token, "Signin success");
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserService.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid Username");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid Password");
        }

        User user = userRepository.findByEmail(username);

        // Retrieve the user's role as an enum
        Role role = user.getRole();

        // Create a list of authorities based on the user's role
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name())); // Use enum for role-based authority

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
