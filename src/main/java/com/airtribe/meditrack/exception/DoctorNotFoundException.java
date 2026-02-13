package com.airtribe.meditrack.exception;

/**
 * Exception thrown when a doctor is not found in the system.
 */
public class DoctorNotFoundException extends Exception {
    
    /**
     * Constructor with message.
     *
     * @param message the error message
     */
    public DoctorNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause.
     *
     * @param message the error message
     * @param cause   the cause of the exception
     */
    public DoctorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
