package com.example.oldstore.exception;

public class UnauthorizedActionException extends RuntimeException {
	
    public UnauthorizedActionException(String message) {
        super(message);
    }
}