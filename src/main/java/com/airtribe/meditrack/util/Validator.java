package com.airtribe.meditrack.util;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.exception.InvalidDataException;
import java.util.regex.Pattern;

/**
 * Validator utility class for validating user input and data.
 * Provides methods for validating names, emails, phone numbers, etc.
 */
public class Validator {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(Constants.EMAIL_REGEX);
    private static final Pattern PHONE_PATTERN = Pattern.compile(Constants.PHONE_REGEX);
    
    /**
     * Validate a name.
     *
     * @param name the name to validate
     * @throws InvalidDataException if name is invalid
     */
    public static void validateName(String name) throws InvalidDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Name cannot be empty");
        }
        if (name.length() < Constants.MIN_NAME_LENGTH || name.length() > Constants.MAX_NAME_LENGTH) {
            throw new InvalidDataException("Name must be between " + Constants.MIN_NAME_LENGTH + 
                    " and " + Constants.MAX_NAME_LENGTH + " characters");
        }
    }
    
    /**
     * Validate an email address.
     *
     * @param email the email to validate
     * @throws InvalidDataException if email is invalid
     */
    public static void validateEmail(String email) throws InvalidDataException {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidDataException("Email cannot be empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidDataException(Constants.ERROR_INVALID_EMAIL);
        }
    }
    
    /**
     * Validate a phone number.
     *
     * @param phone the phone number to validate
     * @throws InvalidDataException if phone is invalid
     */
    public static void validatePhone(String phone) throws InvalidDataException {
        if (phone == null || phone.trim().isEmpty()) {
            throw new InvalidDataException("Phone number cannot be empty");
        }
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            throw new InvalidDataException(Constants.ERROR_INVALID_PHONE);
        }
    }
    
    /**
     * Validate an address.
     *
     * @param address the address to validate
     * @throws InvalidDataException if address is invalid
     */
    public static void validateAddress(String address) throws InvalidDataException {
        if (address == null || address.trim().isEmpty()) {
            throw new InvalidDataException("Address cannot be empty");
        }
    }
    
    /**
     * Validate age.
     *
     * @param age the age to validate
     * @throws InvalidDataException if age is invalid
     */
    public static void validateAge(int age) throws InvalidDataException {
        if (age < 0 || age > 150) {
            throw new InvalidDataException("Age must be between 0 and 150");
        }
    }
    
    /**
     * Validate a positive number (for fees, etc.).
     *
     * @param amount the amount to validate
     * @param fieldName the name of the field
     * @throws InvalidDataException if amount is invalid
     */
    public static void validatePositiveAmount(double amount, String fieldName) throws InvalidDataException {
        if (amount < 0) {
            throw new InvalidDataException(fieldName + " cannot be negative");
        }
    }
    
    /**
     * Validate a positive integer.
     *
     * @param value the value to validate
     * @param fieldName the name of the field
     * @throws InvalidDataException if value is invalid
     */
    public static void validatePositiveInteger(int value, String fieldName) throws InvalidDataException {
        if (value < 0) {
            throw new InvalidDataException(fieldName + " cannot be negative");
        }
    }
    
    /**
     * Check if string is null or empty.
     *
     * @param str the string to check
     * @return true if null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Validate blood group format.
     *
     * @param bloodGroup the blood group to validate
     * @throws InvalidDataException if blood group is invalid
     */
    public static void validateBloodGroup(String bloodGroup) throws InvalidDataException {
        if (!bloodGroup.matches("^(A|B|AB|O)[+-]$")) {
            throw new InvalidDataException("Invalid blood group format. Must be A/B/AB/O with +/-");
        }
    }
    
    /**
     * Validate gender.
     *
     * @param gender the gender to validate
     * @throws InvalidDataException if gender is invalid
     */
    public static void validateGender(String gender) throws InvalidDataException {
        if (!gender.matches("(?i)^(MALE|FEMALE|OTHER)$")) {
            throw new InvalidDataException("Invalid gender. Must be MALE, FEMALE, or OTHER");
        }
    }
    
    // Private constructor to prevent instantiation
    private Validator() {
        throw new AssertionError("Validator class cannot be instantiated");
    }
}
