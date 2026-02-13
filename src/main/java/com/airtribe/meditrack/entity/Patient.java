package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interface_impl.Searchable;

/**
 * Patient entity representing a patient in the clinic.
 * Implements Searchable interface for search functionality.
 */
public class Patient extends Person implements Searchable, Cloneable {
    
    private static final long serialVersionUID = 1L;
    private int age;
    private String gender;
    private String bloodGroup;
    private String medicalHistory;
    private boolean isSeniorCitizen;
    
    /**
     * Constructor for Patient.
     *
     * @param id              the unique identifier
     * @param name            the patient's name
     * @param email           the patient's email
     * @param phone           the patient's phone number
     * @param address         the patient's address
     * @param status          the patient's status
     * @param age             the patient's age
     * @param gender          the patient's gender
     * @param bloodGroup      the patient's blood group
     * @param medicalHistory  the patient's medical history
     * @param isSeniorCitizen whether patient is a senior citizen
     */
    public Patient(long id, String name, String email, String phone, String address, String status,
                   int age, String gender, String bloodGroup, String medicalHistory, boolean isSeniorCitizen) {
        super(id, name, email, phone, address, status);
        this.age = age;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.medicalHistory = medicalHistory;
        this.isSeniorCitizen = isSeniorCitizen;
    }
    
    // Getters and Setters
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getBloodGroup() {
        return bloodGroup;
    }
    
    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
    
    public String getMedicalHistory() {
        return medicalHistory;
    }
    
    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    
    public boolean isSeniorCitizen() {
        return isSeniorCitizen;
    }
    
    public void setSeniorCitizen(boolean seniorCitizen) {
        isSeniorCitizen = seniorCitizen;
    }
    
    @Override
    public boolean matches(String criteria) {
        String lowerCriteria = criteria.toLowerCase();
        return name.toLowerCase().contains(lowerCriteria) ||
               email.toLowerCase().contains(lowerCriteria) ||
               phone.contains(criteria) ||
               bloodGroup.equalsIgnoreCase(criteria);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", medicalHistory='" + medicalHistory + '\'' +
                ", isSeniorCitizen=" + isSeniorCitizen +
                ", status='" + status + '\'' +
                '}';
    }
}
