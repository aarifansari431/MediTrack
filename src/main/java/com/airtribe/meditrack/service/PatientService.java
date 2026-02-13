package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.exception.PatientNotFoundException;
import com.airtribe.meditrack.util.*;
import java.util.*;

/**
 * PatientService handles all patient-related operations.
 * Implements CRUD operations and search functionality.
 */
public class PatientService {
    
    private final DataStore<Patient> patientStore;
    
    /**
     * Constructor for PatientService.
     */
    public PatientService() {
        this.patientStore = new DataStore<>("PatientStore");
    }
    
    /**
     * Add a new patient to the system.
     *
     * @param name             the patient's name
     * @param email            the patient's email
     * @param phone            the patient's phone
     * @param address          the patient's address
     * @param age              the patient's age
     * @param gender           the patient's gender
     * @param bloodGroup       the patient's blood group
     * @param medicalHistory   the patient's medical history
     * @param isSeniorCitizen  whether patient is a senior citizen
     * @return the created patient
     * @throws InvalidDataException if any validation fails
     */
    public Patient addPatient(String name, String email, String phone, String address,
                             int age, String gender, String bloodGroup, String medicalHistory,
                             boolean isSeniorCitizen) throws InvalidDataException {
        
        Validator.validateName(name);
        Validator.validateEmail(email);
        Validator.validatePhone(phone);
        Validator.validateAddress(address);
        Validator.validateAge(age);
        Validator.validateGender(gender);
        Validator.validateBloodGroup(bloodGroup);
        
        long patientId = IdGenerator.generatePatientId();
        Patient patient = new Patient(patientId, name, email, phone, address, "ACTIVE",
                age, gender, bloodGroup, medicalHistory, isSeniorCitizen);
        
        patientStore.add(patient);
        return patient;
    }
    
    /**
     * Get a patient by ID.
     *
     * @param patientId the patient's ID
     * @return the patient
     * @throws PatientNotFoundException if patient not found
     */
    public Patient getPatientById(long patientId) throws PatientNotFoundException {
        for (Patient patient : patientStore.getAll()) {
            if (patient.getId() == patientId) {
                return patient;
            }
        }
        throw new PatientNotFoundException("Patient with ID " + patientId + " not found");
    }
    
    /**
     * Get all patients.
     *
     * @return list of all patients
     */
    public List<Patient> getAllPatients() {
        return patientStore.getAll();
    }
    
    /**
     * Update patient information.
     *
     * @param patientId the patient's ID
     * @param patient   the updated patient
     * @throws PatientNotFoundException if patient not found
     */
    public void updatePatient(long patientId, Patient patient) throws PatientNotFoundException {
        Patient existingPatient = getPatientById(patientId);
        
        existingPatient.setName(patient.getName());
        existingPatient.setEmail(patient.getEmail());
        existingPatient.setPhone(patient.getPhone());
        existingPatient.setAddress(patient.getAddress());
        existingPatient.setAge(patient.getAge());
        existingPatient.setGender(patient.getGender());
        existingPatient.setBloodGroup(patient.getBloodGroup());
        existingPatient.setMedicalHistory(patient.getMedicalHistory());
        existingPatient.setSeniorCitizen(patient.isSeniorCitizen());
    }
    
    /**
     * Delete a patient (mark as inactive).
     *
     * @param patientId the patient's ID
     * @throws PatientNotFoundException if patient not found
     */
    public void deletePatient(long patientId) throws PatientNotFoundException {
        Patient patient = getPatientById(patientId);
        patient.setStatus("INACTIVE");
    }
    
    /**
     * Search patients by criteria.
     *
     * @param criteria the search criteria
     * @return list of matching patients
     */
    public List<Patient> searchPatients(String criteria) {
        List<Patient> results = new ArrayList<>();
        for (Patient patient : patientStore.getAll()) {
            if (patient.matches(criteria)) {
                results.add(patient);
            }
        }
        return results;
    }
    
    /**
     * Get patients by blood group.
     *
     * @param bloodGroup the blood group to search for
     * @return list of patients with the given blood group
     */
    public List<Patient> getPatientsByBloodGroup(String bloodGroup) {
        return patientStore.filter(patient -> patient.getBloodGroup().equals(bloodGroup) 
                && "ACTIVE".equals(patient.getStatus()));
    }
    
    /**
     * Get senior citizen patients.
     *
     * @return list of senior citizen patients
     */
    public List<Patient> getSeniorCitizenPatients() {
        return patientStore.filter(patient -> patient.isSeniorCitizen() 
                && "ACTIVE".equals(patient.getStatus()));
    }
    
    /**
     * Get the total number of patients.
     *
     * @return the count of active patients
     */
    public int getTotalPatientsCount() {
        return (int) patientStore.getAll().stream()
                .filter(p -> "ACTIVE".equals(p.getStatus()))
                .count();
    }
    
    /**
     * Clone a patient (for demonstration of cloning).
     *
     * @param patientId the patient's ID to clone
     * @return a cloned copy of the patient
     * @throws PatientNotFoundException if patient not found
     */
    public Patient clonePatient(long patientId) throws PatientNotFoundException {
        Patient original = getPatientById(patientId);
        try {
            return (Patient) original.clone();
        } catch (CloneNotSupportedException e) {
            throw new PatientNotFoundException("Failed to clone patient", e);
        }
    }
}
