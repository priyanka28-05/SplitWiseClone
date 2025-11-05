package com.example.splitwise.exception;

/**
 * Exception thrown when a user tries to settle an amount
 * which exceeds the balance they owe.
 */
public class InsufficientBalanceException extends RuntimeException {

    /**
     * Default constructor.
     */
    public InsufficientBalanceException() {
        super();
    }

    /**
     * Constructor with a custom message.
     * @param message detailed exception message
     */
    public InsufficientBalanceException(String message) {
        super(message);
    }
}
