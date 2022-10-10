package com.bridgelabz.UserManagement.service;

import com.bridgelabz.UserManagement.dto.UserDTO;
import com.bridgelabz.UserManagement.model.User;

public interface IUserService {
    User addUser(UserDTO userDTO);
}
