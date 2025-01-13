package com.example.loginauthapi.validation;


public class ValidationException extends RuntimeException {

    public ValidationException(String message){
        super(message);
    }
}
