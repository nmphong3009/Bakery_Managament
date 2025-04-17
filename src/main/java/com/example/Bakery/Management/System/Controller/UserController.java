package com.example.Bakery.Management.System.Controller;

import com.example.Bakery.Management.System.DTOS.Response.UserResponse;
import com.example.Bakery.Management.System.Enum.Role;
import com.example.Bakery.Management.System.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("user")
public class UserController {
    @Lazy
    private UserService userService;

    @GetMapping("admin/getAll")
    public ResponseEntity<List<UserResponse>> getAllUser(){
        List<UserResponse> userResponseList = userService.getAllUser();
        return ResponseEntity.ok(userResponseList);
    }

    @GetMapping("admin/getByRole")
    public ResponseEntity<List<UserResponse>> getUserByRole(@RequestParam Role role){
        List<UserResponse> userResponseList = userService.getUserByRole(role);
        return ResponseEntity.ok(userResponseList);
    }

    @PutMapping("admin/updateRole/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestParam Role role){
        return ResponseEntity.ok(userService.updateRole(id, role));
    }

    @GetMapping("getUser")
    public ResponseEntity<UserResponse> getUser(){
        return userService.getUser();
    }

    @GetMapping("/admin/getUser/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @DeleteMapping("admin/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.delete(id));
    }
}

