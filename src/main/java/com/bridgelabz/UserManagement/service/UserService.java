package com.bridgelabz.UserManagement.service;

import com.bridgelabz.UserManagement.dto.UserDTO;
import com.bridgelabz.UserManagement.model.User;
import com.bridgelabz.UserManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService{
@Autowired
    UserRepository userRepository;
    @Override
    public User addUser(UserDTO userDTO) {
        User user = new User(userDTO);
        userRepository.save(user);
        return user;
    }
}
