package com.airtribe.meditrack.util;

/**
 * AIHelper utility class for optional AI-powered recommendations.
 * Provides methods for generating suggestions and insights.
 * This is an optional advanced feature for the MediTrack system.
 */
public class AIHelper {
    
    /**
     * Generate appointment recommendations for a patient.
     *
     * @param medicalHistory the patient's medical history
     * @param lastVisitDays  days since last visit
     * @return recommendation message
     */
    public static String getAppointmentRecommendation(String medicalHistory, long lastVisitDays) {
        if (lastVisitDays > 365) {
            return "** RECOMMENDATION: Patient hasn't visited for over a year. Consider scheduling a check-up.";
        }
        
        if (medicalHistory != null && !medicalHistory.isEmpty()) {
            String history = medicalHistory.toLowerCase();
            if (history.contains("diabetes") && lastVisitDays > 90) {
                return "** RECOMMENDATION: Diabetes patient should have follow-up within 3 months.";
            }
            if (history.contains("hypertension") && lastVisitDays > 180) {
                return "** RECOMMENDATION: Hypertension patient should have follow-up within 6 months.";
            }
        }
        
        return null;
    }
    
    /**
     * Analyze appointment patterns for a doctor.
     *
     * @param totalAppointments the total appointments
     * @param completedAppointments the completed appointments
     * @return insights message
     */
    public static String analyzeDoctorPerformance(int totalAppointments, int completedAppointments) {
        if (totalAppointments == 0) {
            return "No appointment data available.";
        }
        
        double completionRate = (double) completedAppointments / totalAppointments * 100;
        
        if (completionRate < 50) {
            return "** ALERT: Low appointment completion rate (" + String.format("%.2f", completionRate) + "%)";
        } else if (completionRate > 90) {
            return "** EXCELLENT: High appointment completion rate (" + String.format("%.2f", completionRate) + "%)";
        }
        
        return "Appointment completion rate: " + String.format("%.2f", completionRate) + "%";
    }
    
    /**
     * Get billing insights for a patient.
     *
     * @param totalBills    the total bills
     * @param pendingAmount the pending amount
     * @param totalAmount   the total amount billed
     * @return insights message
     */
    public static String getBillingInsights(int totalBills, double pendingAmount, double totalAmount) {
        if (totalBills == 0) {
            return "No billing data available.";
        }
        
        double pendingPercentage = (pendingAmount / totalAmount) * 100;
        
        if (pendingPercentage > 50) {
            return "** BILLING ALERT: " + String.format("%.2f", pendingPercentage) + 
                    "% of bills are pending.";
        } else if (pendingPercentage == 0) {
            return "** POSITIVE: All bills have been paid.";
        }
        
        return "Pending bills: " + String.format("%.2f", pendingPercentage) + "%";
    }
    
    /**
     * Suggest pricing based on doctor experience and specialization.
     *
     * @param yearsOfExperience the years of experience
     * @param specialization    the specialization
     * @return suggested consultation fee
     */
    public static double suggestConsultationFee(int yearsOfExperience, String specialization) {
        double baseFee = 500.0;
        
        // Add fee based on experience
        if (yearsOfExperience >= 10) {
            baseFee += 500.0;
        } else if (yearsOfExperience >= 5) {
            baseFee += 300.0;
        }
        
        // Add fee based on specialization
        if (specialization != null) {
            String spec = specialization.toLowerCase();
            if (spec.contains("surgery") || spec.contains("cardiac")) {
                baseFee += 500.0;
            } else if (spec.contains("orthopedic")) {
                baseFee += 400.0;
            }
        }
        
        return baseFee;
    }
    
    // Private constructor to prevent instantiation
    private AIHelper() {
        throw new AssertionError("AIHelper class cannot be instantiated");
    }
}
