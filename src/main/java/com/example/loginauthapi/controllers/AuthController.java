package com.example.loginauthapi.controllers;

import com.example.loginauthapi.domain.role.AppRole;
import com.example.loginauthapi.domain.user.User;
import com.example.loginauthapi.dto.LoginRequestDTO;
import com.example.loginauthapi.dto.RegisterRequestDTO;
import com.example.loginauthapi.dto.ResponseDTO;
import com.example.loginauthapi.infra.security.TokenService;
import com.example.loginauthapi.repositories.RoleRepository;
import com.example.loginauthapi.repositories.UserRepository;
import com.example.loginauthapi.validation.ValidationException;
import com.example.loginauthapi.validation.ValidationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Data
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final TokenService tokenService;

    @Autowired
    final ValidationService validationService;

    @GetMapping("/")
    public String Home(){
        return ("Welcome home");
    }


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            String userLogged = "User Logged Successfully";
            System.out.println(userLogged + " Name: " + user.getName());
            return ResponseEntity.ok(new ResponseDTO(userLogged, token));
        }
        return ResponseEntity.badRequest().build();

    }


//    @PostMapping("/register")
//    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
//
//        //verify if the user is in the database
//        Optional<User> user = this.repository.findByEmail(body.email());
//
//        //if its in database, return bad request
//        if (repository.findByEmail(body.email()).isPresent()){
//            return ResponseEntity.status(409).body("User already exists with this email");
//        }
//
//            User newUser = new User();
//            newUser.setName(body.name());
//            newUser.setEmail(body.email());
//            newUser.setPassword(passwordEncoder.encode(body.password()));
//
//            //save the new user in the database
//            this.repository.save(newUser);
//
//            //Generate the token of authentication
//            String token = this.tokenService.generateToken(newUser);
//
//            //return the request with the name of the user and token
//            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
//
//    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        try {
            // Validate the email and password
            validationService.validateEmail(body.email());
            validationService.validatePassword(body.password());

            // Check if the user already exists
            if (repository.findByEmail(body.email()).isPresent()) {
                System.err.println("Error: User already exists with email " + body.email());
                return ResponseEntity.status(409).body("User already exists with this email");
            }

            // Create a new user and save to the database
            User newUser = new User();
            newUser.setName(body.name());
            newUser.setEmail(body.email());
            newUser.setPassword(passwordEncoder.encode(body.password()));

            //Assign default 'ROLE_USER'
            AppRole userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() ->new RuntimeException("ROLE USER NOT FOUND"));
            newUser.setRoles(Collections.singleton(userRole));


            repository.save(newUser);

            // Generate and return the token
            String token = tokenService.generateToken(newUser);

            String successMessage = "User Registered Successfully";
            System.out.println(successMessage + " Name: " + newUser.getName() );
            return ResponseEntity.ok(new ResponseDTO(successMessage, token));
        } catch (ValidationException e) {
            // Handle validation errors
            System.err.println("Validation error: " + e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception e) {
            // Handle unexpected errors
            System.err.println("Unexpected error during user registration: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("An unexpected error occurred");
        }
    }
}
