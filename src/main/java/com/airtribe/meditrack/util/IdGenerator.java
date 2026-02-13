package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class for generating unique IDs.
 * Uses AtomicLong for thread-safety in concurrent environments.
 */
public class IdGenerator {
    
    private static final AtomicLong doctorIdCounter = new AtomicLong(1000L);
    private static final AtomicLong patientIdCounter = new AtomicLong(2000L);
    private static final AtomicLong appointmentIdCounter = new AtomicLong(3000L);
    private static final AtomicLong billIdCounter = new AtomicLong(4000L);
    
    /**
     * Generate a unique Doctor ID.
     *
     * @return the next doctor ID
     */
    public static long generateDoctorId() {
        return doctorIdCounter.incrementAndGet();
    }
    
    /**
     * Generate a unique Patient ID.
     *
     * @return the next patient ID
     */
    public static long generatePatientId() {
        return patientIdCounter.incrementAndGet();
    }
    
    /**
     * Generate a unique Appointment ID.
     *
     * @return the next appointment ID
     */
    public static long generateAppointmentId() {
        return appointmentIdCounter.incrementAndGet();
    }
    
    /**
     * Generate a unique Bill ID.
     *
     * @return the next bill ID
     */
    public static long generateBillId() {
        return billIdCounter.incrementAndGet();
    }
    
    /**
     * Reset all counters (useful for testing).
     */
    public static void resetCounters() {
        doctorIdCounter.set(1000L);
        patientIdCounter.set(2000L);
        appointmentIdCounter.set(3000L);
        billIdCounter.set(4000L);
    }
    
    // Private constructor to prevent instantiation
    private IdGenerator() {
        throw new AssertionError("IdGenerator class cannot be instantiated");
    }
}
