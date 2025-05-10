package com.FormulaFan.exception;

/**
 * This class defines a custom exception that is thrown when a user tries to register with an existing username
 */
public class DuplicateAccountException extends Exception{
    public DuplicateAccountException(String message) {
        super(message);
    }
    
}
