package com.airtribe.meditrack.constants;

/**
 * Constants class containing application-wide configuration values and messages.
 * Follows SOLID principles by centralizing constants.
 */
public class Constants {
    
    // Application Info
    public static final String APP_NAME = "MediTrack";
    public static final String APP_VERSION = "1.0";
    public static final String APP_DESCRIPTION = "Clinic & Appointment Management System";
    
    // Date and Time Formats
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "HH:mm";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm";
    
    // Validation Constants
    public static final int MIN_NAME_LENGTH = 2;
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MIN_PHONE_LENGTH = 10;
    public static final int MAX_PHONE_LENGTH = 15;
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String PHONE_REGEX = "^[0-9]{10,15}$";
    
    // Appointment Constants
    public static final double CONSULTATION_FEE = 500.0;
    public static final int MIN_APPOINTMENT_SLOTS_PER_DAY = 10;
    public static final int MAX_APPOINTMENT_SLOTS_PER_DAY = 50;
    
    // Bill Constants
    public static final double TAX_RATE = 0.18; // 18% GST
    public static final double DISCOUNT_THRESHOLD = 5000.0;
    public static final double SENIOR_CITIZEN_DISCOUNT = 0.10; // 10% discount
    
    // File Paths
    public static final String DATA_DIRECTORY = "data/";
    public static final String DOCTORS_FILE = DATA_DIRECTORY + "doctors.csv";
    public static final String PATIENTS_FILE = DATA_DIRECTORY + "patients.csv";
    public static final String APPOINTMENTS_FILE = DATA_DIRECTORY + "appointments.csv";
    public static final String BILLS_FILE = DATA_DIRECTORY + "bills.csv";
    
    // Menu Labels
    public static final String MAIN_MENU_TITLE = "===== " + APP_NAME + " ==== Main Menu =====";
    public static final String DOCTOR_MENU_TITLE = "======== Doctor Management ========";
    public static final String PATIENT_MENU_TITLE = "======== Patient Management ========";
    public static final String APPOINTMENT_MENU_TITLE = "======== Appointment Management ========";
    public static final String BILLING_MENU_TITLE = "======== Billing Management ========";
    
    // Status Constants
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";
    public static final String APPOINTMENT_STATUS_SCHEDULED = "SCHEDULED";
    public static final String APPOINTMENT_STATUS_COMPLETED = "COMPLETED";
    public static final String APPOINTMENT_STATUS_CANCELLED = "CANCELLED";
    
    // Error Messages
    public static final String ERROR_INVALID_INPUT = "Invalid input. Please try again.";
    public static final String ERROR_INVALID_ID = "Invalid ID. Please enter a valid numeric ID.";
    public static final String ERROR_NOT_FOUND = "Record not found.";
    public static final String ERROR_DUPLICATE = "Record already exists.";
    public static final String ERROR_INVALID_DATE = "Invalid date format. Use " + DATE_FORMAT;
    public static final String ERROR_INVALID_EMAIL = "Invalid email format.";
    public static final String ERROR_INVALID_PHONE = "Invalid phone number. Must be 10-15 digits.";
    
    // Success Messages
    public static final String SUCCESS_CREATED = "Record created successfully.";
    public static final String SUCCESS_UPDATED = "Record updated successfully.";
    public static final String SUCCESS_DELETED = "Record deleted successfully.";
    
    // Divider
    public static final String DIVIDER = "================================";
    public static final String DOUBLE_DIVIDER = "====================================";
    
    // Private constructor to prevent instantiation
    private Constants() {
        throw new AssertionError("Constants class cannot be instantiated");
    }
}
