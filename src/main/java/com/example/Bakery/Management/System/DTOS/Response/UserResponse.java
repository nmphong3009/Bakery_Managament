package com.example.Bakery.Management.System.DTOS.Response;

import com.example.Bakery.Management.System.Enum.Role;
import lombok.*;

@Getter
@Setter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String idCard;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Role role;
}
