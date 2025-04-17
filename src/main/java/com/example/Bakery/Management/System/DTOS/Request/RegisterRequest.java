package com.example.Bakery.Management.System.DTOS.Request;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String idCard;
    private String password;
    private String confirmPassword;
    private String fullName;
    private String phoneNumber;
}
