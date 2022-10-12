package com.bridgelabz.UserManagement.controller;

import com.bridgelabz.UserManagement.dto.LoginDTO;
import com.bridgelabz.UserManagement.dto.ResponseDTO;
import com.bridgelabz.UserManagement.dto.UserDTO;
import com.bridgelabz.UserManagement.model.User;
import com.bridgelabz.UserManagement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService iuserService;

    @PostMapping("/insert_user")
    public ResponseEntity<ResponseDTO> AddUser(@RequestBody UserDTO userDTO){
        String response = iuserService.addUser(userDTO);
        ResponseDTO responseDTO =new  ResponseDTO ("User Successfully added",response);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }
    /**
     * Post Api for Login for particular user
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody LoginDTO loginDTO) {
        User response = iuserService.loginUser(loginDTO);
        ResponseDTO responseDTO = new ResponseDTO("Login Successful!", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    /**
     * Get Api for Forgot password with email*/
    @GetMapping("/forgotpassword/{email}")
    public ResponseEntity<ResponseDTO> forgotPasswordByEmail(@PathVariable String email) {
        String response = iuserService.forgotPassword(email);
        ResponseDTO respDTO = new ResponseDTO("*** Link send successfully ***", response);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }
    /**
     * Post Api for resetPassword user data
     */
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody LoginDTO loginDto) {
        String response = iuserService.resetPassword(loginDto);
        return new ResponseEntity(response, HttpStatus.OK);
    }
    @GetMapping("/get/{token}")
    public ResponseEntity<ResponseDTO> getUserData(@PathVariable  String token) {
        User userData= null;
        userData = iuserService.getUserDataById(token);
        ResponseDTO respDTO= new ResponseDTO("Get Call For ID Successful", userData);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }
    @GetMapping("/verify/{token}")
    public ResponseEntity<ResponseDTO> verifyUser(@PathVariable String token){
        ResponseDTO responseDTO = new ResponseDTO("User verified successfully", iuserService.verifyUser(token));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<ResponseDTO> getAllUsers() {
        List<User> newUser = iuserService.getAllUsers();
        ResponseDTO responseDTO = new ResponseDTO("All Users records retrieved successfully !", newUser);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> editData(@PathVariable int id, @Valid @RequestBody UserDTO userDto) {
        String response = iuserService.editById(id, userDto);
        ResponseDTO responseDTO = new ResponseDTO("Updated Book Details Successfully", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getGroupByAge")
    public ResponseEntity<ResponseDTO> getAllUsersByAge() {
        List<User> newUser = iuserService.getAllUsersByAge();
        ResponseDTO responseDTO = new ResponseDTO("All Users Age records retrieved successfully !", newUser);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }
}
