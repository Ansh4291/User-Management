package com.bridgelabz.UserManagement.model;

import com.bridgelabz.UserManagement.dto.LoginDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int loginHistoryId;
    int id;
    String emailId;
    LocalDateTime loginDataTime;
}
