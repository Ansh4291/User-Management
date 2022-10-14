package com.bridgelabz.UserManagement.service;

import com.bridgelabz.UserManagement.dto.LoginDTO;
import com.bridgelabz.UserManagement.dto.UserDTO;
import com.bridgelabz.UserManagement.dto.UserPrivilegeDTO;
import com.bridgelabz.UserManagement.model.LoginHistory;
import com.bridgelabz.UserManagement.model.User;
import com.bridgelabz.UserManagement.model.UserPrivilege;

import java.util.List;

public interface IUserService {
    String addUser(UserDTO userDTO);

    User verifyUser(String token);

    User getUserDataById(String token);

    User loginUser(LoginDTO loginDTO);

    String forgotPassword(String email);

    String resetPassword(LoginDTO loginDto);

    List<User> getAllUsers();

    String editById(int id, UserDTO userDto);

    int getGroupByAgeUnder18();
    int getAllUsersByAge18to40();
    int getGroupByAgeAbove40();

    int getAllUsersByLocation(String address);

    int getAllUsersByGender(String gender);

    List<User> getRecentRegistrationList();

    List<User> getAllRegistrationList();

    UserPrivilege addPermission(UserPrivilegeDTO userPrivilegeDto);

    List<LoginHistory> getLoginHistory(String email);

    String findProfilePic(int id);
}
