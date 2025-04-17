package com.example.Bakery.Management.System.DTOS.Request;

import com.example.Bakery.Management.System.Enum.Role;
import lombok.*;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String idCard;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Role role;
}
