package com.bridgelabz.UserManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private String message;
    private Object object;

    public ResponseDTO(String s, String response) {
        this.message = s;
        this.object = response;
    }
}
