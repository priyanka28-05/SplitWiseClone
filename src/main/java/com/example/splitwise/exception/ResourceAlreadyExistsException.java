package com.example.splitwise.exception;

/**
 * Exception thrown when trying to create a resource that already exists
 * (user, group, expense, etc.).
 */
public class ResourceAlreadyExistsException extends RuntimeException {

    /**
     * Default constructor.
     */
    public ResourceAlreadyExistsException() {
        super();
    }

    /**
     * Constructor with a custom message.
     * @param message detailed exception message
     */
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
