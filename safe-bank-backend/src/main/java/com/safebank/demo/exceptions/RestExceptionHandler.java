package com.safebank.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.safebank.demo.exceptions.customExceptions.AccountNotFound;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(AccountNotFound.class)
    private ResponseEntity<RestErrorMessage> accountNotFoundHandler(AccountNotFound exception) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
        
    }





}

