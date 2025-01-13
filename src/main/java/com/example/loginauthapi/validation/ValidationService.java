package com.example.loginauthapi.validation;


import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidationService {

    public void validateEmail(String email) throws ValidationException{
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (!Pattern.compile(emailRegex).matcher(email).matches()) {
            throw new ValidationException("Invalid email format");
        }
    }


    public void validatePassword(String password) throws ValidationException {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!Pattern.compile(passwordRegex).matcher(password).matches()) {
            throw new ValidationException(
                    "Password must be at least 8 characters long, include an uppercase letter, a lowercase letter, a digit, and a special character"
            );
        }
    }
}
