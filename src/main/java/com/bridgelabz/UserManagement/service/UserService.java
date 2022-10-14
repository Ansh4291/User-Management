package com.bridgelabz.UserManagement.service;

import com.bridgelabz.UserManagement.dto.LoginDTO;
import com.bridgelabz.UserManagement.dto.UserDTO;
import com.bridgelabz.UserManagement.dto.UserPrivilegeDTO;
import com.bridgelabz.UserManagement.exeptions.UserException;
import com.bridgelabz.UserManagement.model.LoginHistory;
import com.bridgelabz.UserManagement.model.User;
import com.bridgelabz.UserManagement.model.UserPrivilege;
import com.bridgelabz.UserManagement.repository.LoginHistoryRepo;
import com.bridgelabz.UserManagement.repository.UserPrivilegeRepo;
import com.bridgelabz.UserManagement.repository.UserRepository;
import com.bridgelabz.UserManagement.util.EmailSenderService;
import com.bridgelabz.UserManagement.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{
@Autowired
    UserRepository userRepository;
@Autowired
    EmailSenderService emailSenderService;
@Autowired
    TokenUtil tokenUtil;
@Autowired
    LoginHistoryRepo loginHistoryRepo;
@Autowired
    UserPrivilegeRepo userPrivilegeRepo;
    @Override
    public String addUser(UserDTO userDTO) {
        User user = new User(userDTO);
        userRepository.save(user);
        String token = tokenUtil.createToken(user.getId());
        emailSenderService.sendEmail(user.getEmailId(), "Added Your Details", "http://localhost:8081/user/verify/"+token);
        return token;
    }

    @Override
    public User  getUserDataById(String token) {
        int id = tokenUtil.decodeToken(token);
        return userRepository.findById(id).orElseThrow(() -> new UserException
                ("Employee id  " + id + " note Found "));
    }

    @Override
    public User verifyUser(String token) {
        User user = this.getUserDataById(token);
        user.setVerified(true);
        userRepository.save(user);
        return user;
    }

    @Override
    public User loginUser(LoginDTO loginDTO) {
        Optional<User> userDetails = Optional.ofNullable(userRepository.findByEmail(loginDTO.getEmail()));
        if (userDetails.isPresent()) {
            boolean userDetails1 = userDetails.get().isVerified();
            //String pass = login.get().getPassword();
            if (userDetails.get().getPassword().equals(loginDTO.getPassword()) && userDetails1 == true) {
                LocalDateTime loginDateTime = LocalDateTime.now();
                LoginHistory loginHistory = new LoginHistory();
                loginHistory.setLoginDataTime(loginDateTime);
                loginHistory.setEmailId(loginDTO.getEmail());
                loginHistory.setId(userDetails.get().getId());
                loginHistoryRepo.save(loginHistory);
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

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public String editById(int id, UserDTO userDto) {
        User editUser = userRepository.findById(id).orElse(null);
        if (editUser != null) {
            editUser.setFirstName(userDto.getFirstName());
            editUser.setMiddleName(userDto.getMiddleName());
            editUser.setLastName(userDto.getLastName());
            editUser.setAge(userDto.getAge());
            editUser.setGender(userDto.getGender());
            editUser.setContactNo(userDto.getContactNo());
            editUser.setEmailId(userDto.getEmailId());
            editUser.setPassword(userDto.getPassword());
            editUser.setAddress(userDto.getAddress());
            editUser.setRole(userDto.getRole());
            userRepository.save(editUser);
            String token = tokenUtil.createToken(editUser.getId());
            emailSenderService.sendEmail(editUser.getEmailId(), "Added Your Details", "http://localhost:8081/user/verify/" + token);
            return token;
        } else
            throw new UserException("Id:" + id + " is not present ");
    }

    @Override
    public int getGroupByAgeUnder18() {
        List<User> users = userRepository.getGroupByAgeUnder18();
        return users.size();
    }

    @Override
    public int getAllUsersByAge18to40() {
        List<User> users = userRepository.findByAge();
        return users.size();
    }

    @Override
    public int getGroupByAgeAbove40() {
        List<User> users = userRepository.getGroupByAgeAbove40();
        return users.size();
    }

    @Override
    public int getAllUsersByLocation(String address) {
        List<User> users = userRepository.findByAddress(address);
        return users.size();

    }

    @Override
    public int getAllUsersByGender(String gender) {
        List<User> users = userRepository.findByGender(gender);
        int obtained= users.size();
        List<User>userList = getAllUsers();
        int total = userList.size();
        int  percentage= obtained * 100 / total;
        return percentage;
    }

    @Override
    public List<User> getRecentRegistrationList() {
        List<User> user =  userRepository.getRecentRegistration();
        return user;
    }

    @Override
    public List<User> getAllRegistrationList() {
        List<User> user =  userRepository.getAllRegistration();
        return user;
    }

    @Override
    public UserPrivilege addPermission(UserPrivilegeDTO userPrivilegeDto) {
        Optional<User> user = userRepository.findById(userPrivilegeDto.getId());
        if (user.isPresent()) {
            UserPrivilege details = new UserPrivilege(user.get(), userPrivilegeDto.isAddDashboard(), userPrivilegeDto.isDeleteDashboard(), userPrivilegeDto.isModifyDashboard(), userPrivilegeDto.isReadDashboard(),
                    userPrivilegeDto.isAddSettings(), userPrivilegeDto.isDeleteSettings(), userPrivilegeDto.isModifySettings(), userPrivilegeDto.isReadSettings(),
                    userPrivilegeDto.isAddUsersInformation(), userPrivilegeDto.isDeleteUsersInformation(), userPrivilegeDto.isModifyUsersInformation(), userPrivilegeDto.isReadUsersInformation(),
                    userPrivilegeDto.isAddWebPage1(), userPrivilegeDto.isDeleteWebPage1(), userPrivilegeDto.isModifyWebPage1(), userPrivilegeDto.isReadWebPage1(),
                    userPrivilegeDto.isAddWebPage2(), userPrivilegeDto.isDeleteWebPage2(), userPrivilegeDto.isModifyWebPage2(), userPrivilegeDto.isReadWebPage2(),
                    userPrivilegeDto.isAddWebPage3(), userPrivilegeDto.isDeleteWebPage3(), userPrivilegeDto.isModifyWebPage3(), userPrivilegeDto.isReadWebPage3());
            userPrivilegeRepo.save(details);
            return details;
        } else
            throw new UserException(" user Id is invalid");
    }

    @Override
    public List<LoginHistory> getLoginHistory(String email) {
        List<LoginHistory> loginHistory = loginHistoryRepo.findByEmail(email);
        if (loginHistory.isEmpty())
            throw new UserException("No login history");
        else {
            return loginHistory;
        }
    }

    @Override
    public String findProfilePic(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get().getProfilePic();
        else {
            return "Profile pic Path is not present.......";
        }
    }

}
