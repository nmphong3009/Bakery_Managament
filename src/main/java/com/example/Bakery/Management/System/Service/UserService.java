package com.example.Bakery.Management.System.Service;

import com.example.Bakery.Management.System.DTOS.Response.UserResponse;
import com.example.Bakery.Management.System.Entity.User;
import com.example.Bakery.Management.System.Enum.Role;
import com.example.Bakery.Management.System.Repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Lazy
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String idCard) throws UsernameNotFoundException {
        return userRepository.findByIdCard(idCard)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with idCard: " + idCard));
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                return (User) principal;
            }
        }
        return null;
    }

    public boolean isAdmin(){
        User currentUser = getCurrentUser();
        return currentUser.getRole() == Role.ADMIN;
    }

    public List<UserResponse> getAllUser(){
        if (!isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        List<User> userList = userRepository.findAll();
        return userList.stream().map(
                user -> UserResponse.builder()
                        .id(user.getId())
                        .idCard(user.getIdCard())
                        .fullName(user.getFullName())
                        .phoneNumber(user.getPhoneNumber())
                        .role(user.getRole())
                        .build()
        ).toList();
    }

    public List<UserResponse> getUserByRole(Role role) {
        if (!isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        List<User> userList = userRepository.findByRole(role);
        return userList.stream().map(
                user -> UserResponse.builder()
                        .id(user.getId())
                        .idCard(user.getIdCard())
                        .fullName(user.getFullName())
                        .phoneNumber(user.getPhoneNumber())
                        .role(user.getRole())
                        .build()
        ).toList();
    }

    public ResponseEntity<?> updateRole(Long id, Role role){
        if (!isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with idCard: " + id));
        user.setRole(role);
        userRepository.save(user);
        return ResponseEntity.ok("Update Role successful");
    }

    public ResponseEntity<UserResponse> getUser(){
        return new ResponseEntity<>(UserResponse.builder()
                .id(getCurrentUser().getId())
                .idCard(getCurrentUser().getIdCard())
                .fullName(getCurrentUser().getFullName())
                .phoneNumber(getCurrentUser().getPhoneNumber())
                .role(getCurrentUser().getRole())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<UserResponse> getUserById(Long id){
        if (!isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with idCard: " + id));
        return new ResponseEntity<>(UserResponse.builder()
                .id(user.getId())
                .idCard(user.getIdCard())
                .fullName(user.getFullName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build(), HttpStatus.OK);
    }

    public ResponseEntity<?> delete (Long id){
        if (!isAdmin()) {
            throw new RuntimeException("Only admin users can access this resource.");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with idCard: " + id));
        userRepository.delete(user);
        return ResponseEntity.ok("Delete user successful");
    }
}

