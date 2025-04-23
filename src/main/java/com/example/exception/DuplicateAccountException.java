package com.example.exception;

public class DuplicateAccountException extends Exception{
    public DuplicateAccountException(String message) {
        super(message);
    }
    
}
