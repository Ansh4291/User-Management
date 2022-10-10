package com.bridgelabz.UserManagement.controller;

import com.bridgelabz.UserManagement.dto.ResponseDTO;
import com.bridgelabz.UserManagement.dto.UserDTO;
import com.bridgelabz.UserManagement.model.User;
import com.bridgelabz.UserManagement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService iuserService;

    @PostMapping("/insert_user")
    public ResponseEntity<ResponseDTO> AddUser(@RequestBody UserDTO userDTO){
        User response = iuserService.addUser(userDTO);
        ResponseDTO responseDTO =new  ResponseDTO ("User Sucessfully added",response);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }
}
