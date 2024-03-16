package com.kagwe.creswave_code_test.DTO;

import com.kagwe.creswave_code_test.Entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private int status;
    private String message;
    private UserEntity user;
    private String jwt;
}
