package com.bridgelabz.UserManagement.service;

import com.bridgelabz.UserManagement.dto.LoginDTO;
import com.bridgelabz.UserManagement.dto.UserDTO;
import com.bridgelabz.UserManagement.model.User;

public interface IUserService {
    String addUser(UserDTO userDTO);

    User verifyUser(String token);

    User getUserDataById(String token);

    User loginUser(LoginDTO loginDTO);

    String forgotPassword(String email);

    String resetPassword(LoginDTO loginDto);
}
