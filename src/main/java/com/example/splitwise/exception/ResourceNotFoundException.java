package com.example.splitwise.exception;

/**
 * Exception thrown when a requested resource (user, group, expense, etc.) is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Default constructor.
     */
    public ResourceNotFoundException() {
        super();
    }

    /**
     * Constructor with a custom message.
     * @param message detailed exception message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
