package com.bridgelabz.UserManagement.service;

import antlr.Token;
import com.bridgelabz.UserManagement.dto.LoginDTO;
import com.bridgelabz.UserManagement.dto.UserDTO;
import com.bridgelabz.UserManagement.exeptions.UserException;
import com.bridgelabz.UserManagement.model.User;
import com.bridgelabz.UserManagement.repository.UserRepository;
import com.bridgelabz.UserManagement.util.EmailSenderService;
import com.bridgelabz.UserManagement.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{
@Autowired
    UserRepository userRepository;
@Autowired
    EmailSenderService emailSenderService;
@Autowired
    TokenUtil tokenUtil;
    @Override
    public String addUser(UserDTO userDTO) {
        User user = new User(userDTO);
        userRepository.save(user);
        String token = tokenUtil.createToken(user.getId());
        emailSenderService.sendEmail(user.getEmailId(), "Added Your Details", "http://localhost:8081/user/verify/"+token);
        return token;
    }

    @Override
    public User verifyUser(String token) {
        User user = this.getUserDataById(token);
        user.setVerified(true);
        userRepository.save(user);
        return user;
    }

    @Override
    public User  getUserDataById(String token) {
        int id = tokenUtil.decodeToken(token);
        return userRepository.findById(id).orElseThrow(() -> new UserException
                ("Employee id  " + id + " note Found "));
    }

    @Override
    public User loginUser(LoginDTO loginDTO) {
        Optional<User> userDetails = Optional.ofNullable(userRepository.findByEmail(loginDTO.getEmail()));
        if (userDetails.isPresent()) {
            //String pass = login.get().getPassword();
            if (userDetails.get().getPassword().equals(loginDTO.getPassword())) {
                emailSenderService.sendEmail(userDetails.get().getEmailId(), "About Login", "Login Successful!");
                return userDetails.get();
            } else
                emailSenderService.sendEmail(userDetails.get().getEmailId(), "About Login", "Invalid password!");
            throw new UserException("Wrong Password!");
        } else
            throw new UserException("Login Failed, Wrong email or password!!!");
    }

    @Override
    public String forgotPassword(String email) {
        User editdata = userRepository.findByEmail(email);
        if (editdata != null) {
            emailSenderService.sendEmail(editdata.getEmailId(), "About Login", "http://localhost:8081/user/resetPassword/"+email);
            return "Reset link send sucessfully";
        } else
            throw new UserException("Login Failed, Wrong email or password!!!");
    }

    @Override
    public String resetPassword(LoginDTO loginDTO) {
        Optional<User> userDetails = Optional.ofNullable(userRepository.findByEmail(loginDTO.getEmail()));
        String password = loginDTO.getPassword();
        if (userDetails.isPresent()) {
            userDetails.get().setPassword(password);
            userRepository.save(userDetails.get());
            return "Password Changed";
        } else
            return "Invalid Email Address";
    }

}
