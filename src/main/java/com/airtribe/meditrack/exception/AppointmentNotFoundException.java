package main.java.com.airtribe.meditrack.exception;

public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(String msg) {
        super(msg);
    }
}
