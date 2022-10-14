package com.bridgelabz.UserManagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LoginDTO {
    String email;
    @NotEmpty(message = "Address Cannot be Empty")
    String password;
    LocalDateTime updatedTimeStamp = LocalDateTime.now();
}
