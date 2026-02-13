package com.airtribe.meditrack.exception;

/**
 * Exception thrown when a patient is not found in the system.
 */
public class PatientNotFoundException extends Exception {
    
    /**
     * Constructor with message.
     *
     * @param message the error message
     */
    public PatientNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause.
     *
     * @param message the error message
     * @param cause   the cause of the exception
     */
    public PatientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
