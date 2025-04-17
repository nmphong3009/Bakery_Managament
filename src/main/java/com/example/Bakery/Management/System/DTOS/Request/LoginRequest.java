package com.example.Bakery.Management.System.DTOS.Request;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String idCard;
    private String password;
}

