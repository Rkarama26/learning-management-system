package com.example.Teacher_portal.service;

import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.exception.UserException;
import com.example.Teacher_portal.request.LoginRequest;
import com.example.Teacher_portal.response.AuthResponse;


public interface AuthService {
    public AuthResponse registerStudent(User user) throws UserException;

    public AuthResponse registerTeacher(User user) throws UserException;

    public AuthResponse loginUser(LoginRequest loginRequest);
}
