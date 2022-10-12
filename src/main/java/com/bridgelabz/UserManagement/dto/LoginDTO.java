package com.bridgelabz.UserManagement.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@Data
@NoArgsConstructor
public class LoginDTO {
    String email;
    @NotEmpty(message = "Address Cannot be Empty")
    String password;
}
