package com.example.Teacher_portal.service;

import com.example.Teacher_portal.Entity.User;
import com.example.Teacher_portal.exception.UserException;


public interface UserService {

    public User findUserprofileByJwt(String jwt) throws UserException;

    public User updateUserProfile(String jwt, User updatedUser) throws UserException;

    public void deleteUserProfile(Long id) throws UserException;

    public void changePassword(String jwt, String oldPassword, String newPassword) throws UserException;

    public User getUserById(Long userId);

}
