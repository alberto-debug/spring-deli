package com.example.loginauthapi.controllers;



import com.example.loginauthapi.domain.role.AppRole;
import com.example.loginauthapi.domain.user.User;
import com.example.loginauthapi.repositories.RoleRepository;
import com.example.loginauthapi.repositories.UserRepository;
import com.example.loginauthapi.services.AdminService;
import com.example.loginauthapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;


    // Promote a user to admin
    @PostMapping("/promoteToAdmin/{userId}")
    public ResponseEntity<String> promoteToAdmin(@PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            AppRole adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Admin role not found"));
            user.getRoles().add(adminRole);
            userRepository.save(user);
            return ResponseEntity.ok("User promoted to admin");
        }
        return ResponseEntity.status(404).body("User not found");
    }


    //FInd All User
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> findAllUsers(){
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(users);
    }


    // Remove admin role from user
    @PostMapping("/removeAdmin/{userId}")
    public ResponseEntity<String> removeAdminRole(@PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            AppRole adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Admin role not found"));
            user.getRoles().remove(adminRole);
            userRepository.save(user);
            return ResponseEntity.ok("Admin role removed from user");
        }
        return ResponseEntity.status(404).body("User not found");
    }

}
