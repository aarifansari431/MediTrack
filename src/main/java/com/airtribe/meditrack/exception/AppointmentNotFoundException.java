package com.airtribe.meditrack.exception;

/**
 * Exception thrown when an appointment is not found in the system.
 */
public class AppointmentNotFoundException extends Exception {
    
    /**
     * Constructor with message.
     *
     * @param message the error message
     */
    public AppointmentNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructor with message and cause.
     *
     * @param message the error message
     * @param cause   the cause of the exception
     */
    public AppointmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
