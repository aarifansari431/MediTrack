package com.airtribe.meditrack.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * DateUtil utility class for date and time operations.
 * Handles formatting, parsing, and calculations with dates.
 */
public class DateUtil {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    /**
     * Format a LocalDate to string.
     *
     * @param date the date to format
     * @return the formatted date string
     */
    public static String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : "";
    }
    
    /**
     * Format a LocalDateTime to date string.
     *
     * @param dateTime the datetime to format
     * @return the formatted date string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATETIME_FORMATTER) : "";
    }
    
    /**
     * Format a LocalDateTime to time string.
     *
     * @param dateTime the datetime to format
     * @return the formatted time string
     */
    public static String formatTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(TIME_FORMATTER) : "";
    }
    
    /**
     * Parse a string to LocalDate.
     *
     * @param dateString the date string to parse
     * @return the parsed LocalDate
     * @throws DateTimeParseException if parsing fails
     */
    public static LocalDate parseDate(String dateString) throws DateTimeParseException {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }
    
    /**
     * Parse a string to LocalDateTime.
     *
     * @param dateTimeString the datetime string to parse
     * @param timeString     the time string to parse
     * @return the parsed LocalDateTime
     * @throws DateTimeParseException if parsing fails
     */
    public static LocalDateTime parseDateTime(String dateTimeString) throws DateTimeParseException {
        return LocalDateTime.parse(dateTimeString, DATETIME_FORMATTER);
    }
    
    /**
     * Combine date and time strings to LocalDateTime.
     *
     * @param dateString the date string (dd/MM/yyyy)
     * @param timeString the time string (HH:mm)
     * @return the combined LocalDateTime
     * @throws DateTimeParseException if parsing fails
     */
    public static LocalDateTime combineDateAndTime(String dateString, String timeString) 
            throws DateTimeParseException {
        LocalDate date = parseDate(dateString);
        int[] timeParts = parseTime(timeString);
        return LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),
                timeParts[0], timeParts[1]);
    }
    
    /**
     * Parse time string to hours and minutes.
     *
     * @param timeString the time string (HH:mm)
     * @return array [hours, minutes]
     * @throws DateTimeParseException if parsing fails
     */
    private static int[] parseTime(String timeString) throws DateTimeParseException {
        String[] parts = timeString.split(":");
        if (parts.length != 2) {
            throw new DateTimeParseException("Invalid time format", timeString, 0);
        }
        try {
            return new int[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1])};
        } catch (NumberFormatException e) {
            throw new DateTimeParseException("Invalid time format", timeString, 0, e);
        }
    }
    
    /**
     * Check if a date is in the past.
     *
     * @param date the date to check
     * @return true if date is in the past, false otherwise
     */
    public static boolean isDateInPast(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
    
    /**
     * Check if a datetime is in the past.
     *
     * @param dateTime the datetime to check
     * @return true if datetime is in the past, false otherwise
     */
    public static boolean isDateTimeInPast(LocalDateTime dateTime) {
        return dateTime.isBefore(LocalDateTime.now());
    }
    
    /**
     * Get days difference between two dates.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the number of days between dates
     */
    public static long getDaysDifference(LocalDate startDate, LocalDate endDate) {
        return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
    }
    
    /**
     * Get current date.
     *
     * @return the current date
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
    
    /**
     * Get current datetime.
     *
     * @return the current datetime
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
    
    // Private constructor to prevent instantiation
    private DateUtil() {
        throw new AssertionError("DateUtil class cannot be instantiated");
    }
}
