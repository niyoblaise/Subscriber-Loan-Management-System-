package com.example.taskmanager.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ErrorManager {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,String>> handleErrors(RuntimeException ex){
        return ResponseEntity.status(400).body(Map.of("Message: ",ex.getMessage()));
    }
}
