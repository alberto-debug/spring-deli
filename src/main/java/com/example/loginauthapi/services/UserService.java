package com.example.loginauthapi.services;


import com.example.loginauthapi.domain.role.AppRole;
import com.example.loginauthapi.domain.user.User;
import com.example.loginauthapi.repositories.RoleRepository;
import com.example.loginauthapi.repositories.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void createAdminAccount(String name , String email , String password){

        if (userRepository.findByName(name).isEmpty()){
            AppRole role = roleRepository.findByName("ROLE_ADM")
                    .orElseThrow(() -> new RuntimeException("ADM Role not found"));

            User adminUser = new User();
            adminUser.setName(name);
            adminUser.setEmail(email);
            adminUser.setPassword(password);

            userRepository.save(adminUser);
            System.out.println("Admin account created with username: " + name);

        } else {
            System.out.println("Admin account already exists with username: " + name);
        }
    }
}
