package com.example.loginauthapi.controllers;



import com.example.loginauthapi.domain.role.Role;
import com.example.loginauthapi.domain.user.User;
import com.example.loginauthapi.repositories.RoleRepository;
import com.example.loginauthapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    // Assign a role to a user
    @PostMapping("/assign-role")
    public ResponseEntity<String> assignRole(@RequestParam String userEmail, @RequestParam String roleName) {

        // Find the user by email
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //Fetch the ADMIN role from the database
        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseThrow(()-> new RuntimeException("Role ADMIN not found"));
    }


}
