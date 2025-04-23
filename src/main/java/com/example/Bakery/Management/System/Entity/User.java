package com.example.Bakery.Management.System.Entity;

import com.example.Bakery.Management.System.Enum.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "users")
public class User extends AbstractEntity<Long> implements UserDetails {
    @Column
    private String fullName;

    @Column
    private String idCard;

    @Column
    private String phoneNumber;

    @Column
    private String passWord;

    @Column
    private Integer rewardPoints;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Trả về danh sách quyền (authority) của người dùng
        return List.of(() -> role.name());
    }

    @Override
    public String getPassword() {
        return this.passWord;
    }

    @Override
    public String getUsername() {
        return fullName; // Hoặc trường nào đại diện cho tên người dùng
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Tùy thuộc vào yêu cầu của bạn
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Tùy thuộc vào yêu cầu của bạn
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Tùy thuộc vào yêu cầu của bạn
    }

    @Override
    public boolean isEnabled() {
        return true; // Tùy thuộc vào yêu cầu của bạn
    }
}

