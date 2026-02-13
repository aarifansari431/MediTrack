package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.exception.DoctorNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.*;
import java.util.*;

/**
 * DoctorService handles all doctor-related operations.
 * Implements CRUD operations and search functionality.
 */
public class DoctorService {
    
    private final DataStore<Doctor> doctorStore;
    
    /**
     * Constructor for DoctorService.
     */
    public DoctorService() {
        this.doctorStore = new DataStore<>("DoctorStore");
    }
    
    /**
     * Add a new doctor to the system.
     *
     * @param name              the doctor's name
     * @param email             the doctor's email
     * @param phone             the doctor's phone
     * @param address           the doctor's address
     * @param specialization    the doctor's specialization
     * @param consultationFee   the consultation fee
     * @param yearsOfExperience years of experience
     * @param availableDays     available days
     * @param availableHours    available hours
     * @param maxPatientsPerDay max patients per day
     * @return the created doctor
     * @throws InvalidDataException if any validation fails
     */
    public Doctor addDoctor(String name, String email, String phone, String address,
                           String specialization, double consultationFee, int yearsOfExperience,
                           List<String> availableDays, String availableHours, int maxPatientsPerDay)
            throws InvalidDataException {
        
        Validator.validateName(name);
        Validator.validateEmail(email);
        Validator.validatePhone(phone);
        Validator.validateAddress(address);
        Validator.validatePositiveAmount(consultationFee, "Consultation Fee");
        Validator.validatePositiveInteger(yearsOfExperience, "Years of Experience");
        Validator.validatePositiveInteger(maxPatientsPerDay, "Max Patients Per Day");
        
        long doctorId = IdGenerator.generateDoctorId();
        Doctor doctor = new Doctor(doctorId, name, email, phone, address, "ACTIVE",
                specialization, consultationFee, yearsOfExperience, availableDays, availableHours, maxPatientsPerDay);
        
        doctorStore.add(doctor);
        return doctor;
    }
    
    /**
     * Get a doctor by ID.
     *
     * @param doctorId the doctor's ID
     * @return the doctor
     * @throws DoctorNotFoundException if doctor not found
     */
    public Doctor getDoctorById(long doctorId) throws DoctorNotFoundException {
        for (Doctor doctor : doctorStore.getAll()) {
            if (doctor.getId() == doctorId) {
                return doctor;
            }
        }
        throw new DoctorNotFoundException("Doctor with ID " + doctorId + " not found");
    }
    
    /**
     * Get all doctors.
     *
     * @return list of all doctors
     */
    public List<Doctor> getAllDoctors() {
        return doctorStore.getAll();
    }
    
    /**
     * Update doctor information.
     *
     * @param doctorId  the doctor's ID
     * @param doctor    the updated doctor
     * @throws DoctorNotFoundException if doctor not found
     */
    public void updateDoctor(long doctorId, Doctor doctor) throws DoctorNotFoundException {
        Doctor existingDoctor = getDoctorById(doctorId);
        
        existingDoctor.setName(doctor.getName());
        existingDoctor.setEmail(doctor.getEmail());
        existingDoctor.setPhone(doctor.getPhone());
        existingDoctor.setAddress(doctor.getAddress());
        existingDoctor.setSpecialization(doctor.getSpecialization());
        existingDoctor.setConsultationFee(doctor.getConsultationFee());
        existingDoctor.setYearsOfExperience(doctor.getYearsOfExperience());
        existingDoctor.setAvailableDays(doctor.getAvailableDays());
        existingDoctor.setAvailableHours(doctor.getAvailableHours());
        existingDoctor.setMaxPatientsPerDay(doctor.getMaxPatientsPerDay());
    }
    
    /**
     * Delete a doctor (mark as inactive).
     *
     * @param doctorId the doctor's ID
     * @throws DoctorNotFoundException if doctor not found
     */
    public void deleteDoctor(long doctorId) throws DoctorNotFoundException {
        Doctor doctor = getDoctorById(doctorId);
        doctor.setStatus("INACTIVE");
    }
    
    /**
     * Search doctors by criteria.
     *
     * @param criteria the search criteria
     * @return list of matching doctors
     */
    public List<Doctor> searchDoctors(String criteria) {
        List<Doctor> results = new ArrayList<>();
        for (Doctor doctor : doctorStore.getAll()) {
            if (doctor.matches(criteria)) {
                results.add(doctor);
            }
        }
        return results;
    }
    
    /**
     * Get doctors by specialization.
     *
     * @param specialization the specialization to search for
     * @return list of doctors with the given specialization
     */
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorStore.filter(doctor -> doctor.getSpecialization().equalsIgnoreCase(specialization) 
                && "ACTIVE".equals(doctor.getStatus()));
    }
    
    /**
     * Get available doctors for a specific day.
     *
     * @param day the day to search for
     * @return list of doctors available on the given day
     */
    public List<Doctor> getAvailableDoctorsOnDay(String day) {
        return doctorStore.filter(doctor -> doctor.isAvailableOnDay(day) 
                && "ACTIVE".equals(doctor.getStatus()));
    }
    
    /**
     * Get the total number of doctors.
     *
     * @return the count of doctors
     */
    public int getTotalDoctorsCount() {
        return (int) doctorStore.getAll().stream()
                .filter(d -> "ACTIVE".equals(d.getStatus()))
                .count();
    }
}
