package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interface_impl.Searchable;
import java.util.*;

/**
 * Doctor entity representing a healthcare professional.
 * Implements Searchable interface for search functionality.
 */
public class Doctor extends Person implements Searchable {
    
    private static final long serialVersionUID = 1L;
    private String specialization;
    private double consultationFee;
    private int yearsOfExperience;
    private List<String> availableDays;
    private String availableHours; // Format: HH:mm-HH:mm
    private int maxPatientsPerDay;
    
    /**
     * Constructor for Doctor.
     *
     * @param id                  the unique identifier
     * @param name                the doctor's name
     * @param email               the doctor's email
     * @param phone               the doctor's phone number
     * @param address             the doctor's address
     * @param status              the doctor's status
     * @param specialization      the doctor's specialization
     * @param consultationFee     the doctor's consultation fee
     * @param yearsOfExperience   years of experience
     * @param availableDays       list of available days
     * @param availableHours      available hours (format: HH:mm-HH:mm)
     * @param maxPatientsPerDay   maximum patients per day
     */
    public Doctor(long id, String name, String email, String phone, String address, String status,
                  String specialization, double consultationFee, int yearsOfExperience,
                  List<String> availableDays, String availableHours, int maxPatientsPerDay) {
        super(id, name, email, phone, address, status);
        this.specialization = specialization;
        this.consultationFee = consultationFee;
        this.yearsOfExperience = yearsOfExperience;
        this.availableDays = new ArrayList<>(availableDays);
        this.availableHours = availableHours;
        this.maxPatientsPerDay = maxPatientsPerDay;
    }
    
    // Getters and Setters
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public double getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }
    
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }
    
    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
    public List<String> getAvailableDays() {
        return new ArrayList<>(availableDays);
    }
    
    public void setAvailableDays(List<String> availableDays) {
        this.availableDays = new ArrayList<>(availableDays);
    }
    
    public String getAvailableHours() {
        return availableHours;
    }
    
    public void setAvailableHours(String availableHours) {
        this.availableHours = availableHours;
    }
    
    public int getMaxPatientsPerDay() {
        return maxPatientsPerDay;
    }
    
    public void setMaxPatientsPerDay(int maxPatientsPerDay) {
        this.maxPatientsPerDay = maxPatientsPerDay;
    }
    
    /**
     * Check if doctor is available on given day.
     *
     * @param day the day to check
     * @return true if available, false otherwise
     */
    public boolean isAvailableOnDay(String day) {
        return availableDays.contains(day.toUpperCase());
    }
    
    @Override
    public boolean matches(String criteria) {
        String lowerCriteria = criteria.toLowerCase();
        return name.toLowerCase().contains(lowerCriteria) ||
               specialization.toLowerCase().contains(lowerCriteria) ||
               email.toLowerCase().contains(lowerCriteria) ||
               phone.contains(criteria);
    }
    
    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", specialization='" + specialization + '\'' +
                ", consultationFee=" + consultationFee +
                ", yearsOfExperience=" + yearsOfExperience +
                ", availableDays=" + availableDays +
                ", availableHours='" + availableHours + '\'' +
                ", maxPatientsPerDay=" + maxPatientsPerDay +
                ", status='" + status + '\'' +
                '}';
    }
}
